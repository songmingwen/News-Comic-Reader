package com.song.sunset.utils.rxjava;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.MainThread;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

import static androidx.lifecycle.Lifecycle.Event.ON_DESTROY;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/10
 */
public class RxBus {
    private static final RxBus sRxBus = new RxBus();

    public static RxBus getInstance() {
        return sRxBus;
    }

    private final PublishSubject<Object> mSubject = PublishSubject.create();

    private RxBus() {
    }

    /**
     * 发送事件
     * @param object 对应事件实体
     */
    public void post(Object object) {
        mSubject.onNext(object);
    }

    /**
     * 监听 eventType 类型的事件，要自己手动解除注册，否则可能发生内存泄露
     */
    public <T> Observable<T> toObservableForeverOrManualDispose(Class<T> eventType) {
        return mSubject.ofType(eventType);
    }

    /**
     * 监听 eventType 类型的事件，绑定到 ON_DESTROY 上
     *
     * @param eventType 要监听的事件类型
     * @param bindTo    要绑定到的生命周期 owner
     */
    public <T> Observable<T> toObservable(Class<T> eventType, LifecycleOwner bindTo) {
        return toObservable(eventType, bindTo, ON_DESTROY);
    }

    /**
     * 监听 eventType 类型的事件，绑定到特定生命周期上
     *
     * @param eventType 要监听的事件类型
     * @param binderTo  要绑定到的生命周期 owner
     * @param until     到哪个生命周期的时候停止
     */
    public <T> Observable<T> toObservable(Class<T> eventType, LifecycleOwner binderTo, Lifecycle.Event until) {
        return mSubject.ofType(eventType).takeUntil(new BindLifeEvent(binderTo, until));
    }

    /**
     * 监听 eventType 类型的事件，绑定到 View 上，View 卸载即停止监听
     */
    public <T> Observable<T> toObservable(Class<T> eventType, View view) {
        return mSubject.ofType(eventType).takeUntil(new BindViewEvent(view));
    }

    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    /**
     * 将事件绑定到 Lifecycle 上用的 ObservableSource
     */
    private static final class BindLifeEvent implements ObservableSource<Long>, LifecycleEventObserver {
        Observer<? super Long> mObserver;
        final LifecycleOwner mLifecycleOwner;
        final Lifecycle.Event mUntilEvent;

        public BindLifeEvent(LifecycleOwner owner, Lifecycle.Event untilEvent) {
            mUntilEvent = untilEvent;
            mLifecycleOwner = owner;
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                tryBind();
            } else {
                // 非主线程则 post 到主线程
                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@androidx.annotation.NonNull Message msg) {
                        tryBind();
                    }
                };
                Message message = Message.obtain();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    // 立即执行
                    message.setAsynchronous(true);
                }
                // post 到最前方
                handler.sendMessageAtFrontOfQueue(message);
            }
        }

        @Override
        public void subscribe(@NonNull Observer<? super Long> observer) {
            mObserver = observer;
            checkUnbind();
        }

        @Override
        public void onStateChanged(@androidx.annotation.NonNull LifecycleOwner source,
                                   @androidx.annotation.NonNull Lifecycle.Event event) {
            if (event == mUntilEvent) {
                unbind();
            }
        }

        @MainThread
        private void tryBind(){
            mLifecycleOwner.getLifecycle().addObserver(this);
            checkUnbind();
        }

        /**
         * 如果已经 destroy，则直接解绑
         */
        private void checkUnbind() {
            if (mLifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                unbind();
            }
        }

        private void unbind() {
            if (mObserver != null) {
                mObserver.onNext(1L);
            }
        }
    }

    /**
     * 将事件绑定到 View 上用的 ObservableSource
     */
    private static final class BindViewEvent implements ObservableSource<Long> {

        final View view;

        public BindViewEvent(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(@NonNull Observer<? super Long> observer) {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {

                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    observer.onNext(1L);
                }
            });
        }
    }
}
