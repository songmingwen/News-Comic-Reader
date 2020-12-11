package com.song.sunset.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Observable 接力处理，可用于处理预加载
 * <p>
 * 可以通过 {@link #accept(Observable)} 方法无限叠加 Observer，并通过 {@link #end(Observable)} 方法叠加最后一个 Observable，
 * 各个 Observer 被封装成 {@link RelayStep} 顺序处理，只有前面的 Observer 没有发送任何数据就 onComplete 或者 onError 的时候
 * 后面的 Observer 才会执行，否则，前面 Observer 发送的数据会被 RelayStep 顺序传递到最后一步。
 * <p>
 * 注意 {@link #accept(Observable)} 可以执行多次但是 {@link #end(Observable)} 只能执行一次。
 * <p>
 * 使用方式：
 * 1. 假定通过一个 Observer preObserver 进行预加载数据，那么可以
 *
 * <code>Relay.getInstance("唯一名称").accept("preObserver")</code>
 * <p>
 * 来将此 Observer 加入到接力中。此后也可以添加更多 Observer 加入接力。
 * <p>
 * 2. 通过 <code> Relay.getInstance("唯一名称").end(lastObserver) </code> 来结束接力，end 方法返回一个 Observable，
 * 后面走普通 observer 流程即可
 *
 * @author 潘志会 @ Zhihu Inc.
 * @since 2020/08/06
 */
public class Relay<T> {

    /**
     * 全局 Relay 存储
     */
    private static final Map<String, Relay<?>> sRelayMap = new HashMap<>();

    /**
     * 获取一个 Relay 对象，在调用 {@link #end(Observable)} 之前，相同 name 保持单例
     */
    @SuppressWarnings("unchecked")
    @NonNull
    synchronized public static <U> Relay<U> getInstance(@NonNull String name) {
        Relay<U> relay = (Relay<U>) sRelayMap.get(name);
        if (relay == null) {
            relay = new Relay<>(name);
            sRelayMap.put(name, relay);
        }
        return relay;
    }

    @NonNull
    private final String name;

    /**
     * Relay 的各个步骤，不包括最后一步
     */
    private final List<RelayStep<T>> mSteps = new ArrayList<>();

    public Relay(@NonNull String name) {
        this.name = name;
    }

    /**
     * 接收一个 Observable，并在需要的情况下执行此 Observable.
     * <p>
     * 返回一个可监听的 Observable，可通过返回的 Observable 得知这一步的完成情况
     */
    public Observable<T> accept(Observable<T> observable) {
        // observable 如果直接 complete 或者 error，则接着走下一个 observer
        // 如果 observable 走了 onNext，则把数据记录下来，并且下面的 observable 不往下走了
        RelayStep<T> nextStep = new RelayStep<>(observable);
        if (mSteps.isEmpty()) {
            // 如果是第一棒，observable 立即执行
            mSteps.add(nextStep);
            nextStep.run();
        } else {
            // 如果不是第一棒，observable 会传给 preStep，由 preStep 下一棒来决定什么时候执行。
            // 当前 preStep 如果直接 complete 或者 error，那么 nextStep 会接着执行 observable
            // 当前 preStep 如果取到值了，会直接将值传递给 nextStep，nextStep 会直接完成，而不执行 observable
            RelayStep<T> preStep = mSteps.get(mSteps.size() - 1);
            mSteps.add(nextStep);
            preStep.setNextStep(nextStep);
        }
        return nextStep.getObservable();
    }

    /**
     * 只有最后一棒是需要拿结果的。
     * <p>
     * 最后一棒如果能收到上一棒的结果，那么就不再执行。如果上一棒结果为空或者发生错误，那么直接执行最后一棒的 observable
     */
    public Observable<T> end(Observable<T> finalStep) {
        Observable<T> observable;
        if (mSteps.isEmpty()) {
            observable = finalStep;
        } else {
            observable = mSteps.get(mSteps.size() - 1)
                    .getObservable()
                    // 发生错误的时候什么也不做，直接走到 onComplete
                    // 然而理论上 getObservable 并不会走到 onError
                    .onErrorResumeNext(Observable.empty())
                    // 如果上游什么数据也没有，那么再执行 finalStep
                    .switchIfEmpty(finalStep);
        }
        // 此时没有必要再保留在 Map 中了，删除之
        sRelayMap.remove(name);
        mSteps.clear();
        return observable;
    }

    /**
     * 清除之前的所有数据，中断接力
     */
    public void abort() {
        sRelayMap.remove(name);
        mSteps.clear();
    }

    @NonNull
    public String getName() {
        return name;
    }

    /**
     * 获取此 Relay 的结果，如果已经有了的话（不包含最后一步）
     */
    @Nullable
    public T getLastValue() {
        if (mSteps.isEmpty()) {
            return null;
        } else {
            List<T> values = mSteps.get(mSteps.size() - 1).values;
            if (values.isEmpty()) {
                return null;
            } else {
                return values.get(values.size() - 1);
            }
        }
    }

    /**
     * 获取此 Relay 的所有结果
     */
    @NonNull
    public List<T> getValues() {
        if (mSteps.isEmpty()) {
            return Collections.emptyList();
        } else {
            return mSteps.get(mSteps.size() - 1).values;
        }
    }

    /**
     * 接力中的其中一棒，对 Observable 的封装
     */
    static class RelayStep<T> {
        /**
         * 此棒对应的 observable。如果此 observable 发生错误或者已经完成，就执行 {@link #next}
         */
        private final Observable<T> runner;
        /**
         * 此棒是否已经结束（有了结果、直到了 onComplete 或者 onError 都算结束了）
         */
        boolean finished;
        /**
         * 下一棒
         */
        RelayStep<T> next;
        /**
         * 本 Step 数据的订阅发生器
         */
        PublishSubject<T> mSubject = PublishSubject.create();
        /**
         * 已经产生的数据
         */
        List<T> values = new ArrayList<>();


        RelayStep(Observable<T> runner) {
            this.runner = runner;
        }

        /**
         * 在上一棒完全结束后开跑，如果上一棒已经传来了结果，那自己就不用跑了，直接结束就 ok
         */
        void run() {
            // run 的执行一定是因为上一级不存在或者上一级没有任何数据返回并且已经结束，此时 values 必然为空
            if (!values.isEmpty()) {
                // 如果已经有结果了，说明是上一步传下来的
                finishAndRunNext();
                return;
            }
            runner
                    .subscribe(new Observer<T>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            // do nothing
                        }

                        @Override
                        public void onNext(@NonNull T t) {
                            onNextValue(t);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            // 如果已经发送过数据，那么送到下一个 Error?
                            finishAndRunNext();
                        }

                        @Override
                        public void onComplete() {
                            finishAndRunNext();
                        }
                    });
        }

        /**
         * 设置当前棒的下一棒，由 {@link Relay} 执行
         * <p>
         * 如果当前棒已经结束，直接将结果转交给下一棒，下一棒立即执行
         * <p>
         * 如果当前未结束，则等结束（或者出错）后，下一棒才通过 {@link #finishAndRunNext} 执行
         */
        synchronized void setNextStep(@NonNull RelayStep<T> nextStep) {
            next = nextStep;
            // 先把当前所有的值全传给 nextStep
            for (T value : values) {
                nextStep.onNextValue(value);
            }
            if (finished) {
                // 如果已经完成后才设置的 nextStep，那么 nextStep 直接执行 run
                nextStep.run();
            }
        }

        /**
         * 此棒自身 {@link #runner} 走到 onNext 得到结果 / 或者上一棒传给这一棒
         * <p>
         * 此时如果有 next，则传给 next，让 next 执行 onNextValue，否则存储起来并在有 Next 的时候传给 Next
         */
        synchronized void onNextValue(T t) {
            values.add(t);
            if (next != null) {
                // 如果有 next，直接将数据交给 Next
                next.onNextValue(t);
            }
            // 发射到下游
            mSubject.onNext(t);
        }

        /**
         * 结束这一棒，并执行下一棒（如果有的话）
         * <p>
         * 1. 如果有 next，那么 next 也要同时结束
         * 1. 如果没有 next，则要等 next 传过来后，判断当前是否有值，没有值，则 next 正常走，如果有值，next 也要直接 finish 了，它不必再取数据了
         */
        synchronized void finishAndRunNext() {
            if (finished) {
                // 只能 finish 一次
                throw new IllegalStateException("finished too many times");
            }
            finished = true;
            if (next != null) {
                // 本 Step 完成后，再执行 nextStep
                // 理论上来说，如果此 RelayStep 产生了数据，next.run() 会直接走向 finishAndRunNext
                // 如果没有产生数据，比如直接 empty 或者 error 了，
                next.run();
            }
            // 这一步传到 end 中
            mSubject.onComplete();
        }

        /**
         * 为此 RelayStep 产生一个可观测的 Observable。每次都会返回不同的 Observable
         * <p>
         * 永远不会走到 onError。
         */
        synchronized public Observable<T> getObservable() {
            if (finished) {
                // 如果已经完成则直接发送所有数据，如果没有数据，自动就是一个空
                return Observable.fromIterable(values);
            } else {
                // 如果尚未完成，则先发射所有数据，然后再等等 mSubject
                return Observable.concat(Observable.fromIterable(values), mSubject.hide());
            }
        }
    }

}

