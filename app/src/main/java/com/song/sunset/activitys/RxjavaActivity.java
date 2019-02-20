package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.aware.PublishConfig;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.song.sunset.R;
import com.song.sunset.utils.rxjava.RxUtil;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

@Route(path = "/song/rxjava")
public class RxjavaActivity extends AppCompatActivity {


    private static final String TAG = RxjavaActivity.class.getName();

    private ArrayList<Disposable> mDisposables = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, RxjavaActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
        });
    }

    @Override
    protected void onDestroy() {
        if (mDisposables != null) {
            for (Disposable disposable : mDisposables) {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
        super.onDestroy();
    }

    public void create(View view) {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            /*执行顺序：2*/
            Log.e(TAG, "emitter: " + "A");
            emitter.onNext("A");
            /*执行顺序：4*/
            Log.e(TAG, "emitter: " + "B");
            emitter.onNext("B");
            /*执行顺序：6*/
            Log.e(TAG, "emitter: " + "C");
            emitter.onNext("C");
            /*执行顺序：7*/
//            Log.e(TAG, "emitter: " + "Error");
//            emitter.onError(new NullPointerException());
            /*执行顺序：8*/
            Log.e(TAG, "emitter: " + "Complete");
            emitter.onComplete();

        }).compose(RxUtil.getDefaultScheduler())
                .subscribe(new Observer<String>() {
                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        /*执行顺序：1*/
                        Log.e(TAG, "onSubscribe: " + d.isDisposed());
                        mDisposable = d;
                        mDisposables.add(mDisposable);
                    }

                    @Override
                    public void onNext(String s) {
                        /*执行顺序：3、5*/
                        Log.e(TAG, "onNext: " + s);
                        if (TextUtils.equals(s, "B")) {
                            mDisposable.dispose();/* dispose 之后可以发射数据但无法接收数据*/
                            Log.e(TAG, "onSubscribe: " + mDisposable.isDisposed());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }

    public void map(View view) {
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .map(integer -> "map result = " + integer.toString())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * zip 专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，
     * 也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
     * 「并行」
     */
    public void zip(View view) {
        Observable<Integer> intObservable = Observable.just(1, 2, 3, 4);
        Observable<String> stringObservable = Observable.create(emitter -> {
            Thread.sleep(1000);
            emitter.onNext("A");
            Thread.sleep(2000);
            emitter.onNext("B");
            Thread.sleep(3000);
            emitter.onNext("C");
        });
        Disposable disposable = Observable.zip(intObservable, stringObservable,
                (integer, s) -> integer.toString() + "---" + s)
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(o -> Log.e(TAG, "zip: " + o));

        mDisposables.add(disposable);

    }

    /**
     * 对于单一的把两个发射器连接成一个发射器
     * 「串行」
     */
    public void concat(View view) {
        Observable<Integer> intObservable = Observable.just(1, 2, 3);
        Observable<String> stringObservable = Observable.just("A", "B", "C");
        Disposable disposable = Observable.concat(intObservable, stringObservable)
                .compose(RxUtil.getDefaultScheduler())
                .subscribe((Consumer<Serializable>) serializable -> {
                    if (serializable instanceof Integer) {
                        Integer integer = (Integer) serializable;
                        Log.e(TAG, "accept: " + integer.toString());
                    } else if (serializable instanceof String) {
                        String string = (String) serializable;
                        Log.e(TAG, "accept: " + string);
                    }
                });
        mDisposables.add(disposable);

    }

    public void flatMap(View view) {
        Integer[] int01 = {1, 22};
        Integer[] int02 = {333, 4444};
        Integer[] int03 = {55555, 666666};
        ArrayList<Integer[]> list = new ArrayList<>();
        list.add(int01);
        list.add(int02);
        list.add(int03);

        Disposable disposable = Observable.fromIterable(list)
                .flatMap((Function<Integer[], ObservableSource<Integer>>) Observable::fromArray)
                .map(Object::toString)
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(s -> Log.e(TAG, s));
        mDisposables.add(disposable);

    }

    public void concatMap(View view) {
        Integer[] int01 = {1, 22};
        Integer[] int02 = {333, 4444};
        Integer[] int03 = {55555, 666666};
        ArrayList<Integer[]> list = new ArrayList<>();
        list.add(int01);
        list.add(int02);
        list.add(int03);

        Disposable disposable = Observable.fromIterable(list)
                .concatMap((Function<Integer[], ObservableSource<Integer>>) Observable::fromArray)
                .map(Object::toString)
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(s -> Log.e(TAG, s));
        mDisposables.add(disposable);
    }

    public void distinct(View view) {
        Disposable disposable = Observable.just("A", "B", "C", "a", "b", "c", "A", "B", "C")
                .distinct()
                .subscribe(s -> Log.e(TAG, "accept: " + s));
        mDisposables.add(disposable);
    }

    public void filter(View view) {
        Disposable disposable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .filter(integer -> integer < 7)
                .subscribe(integer -> Log.e(TAG, "accept: " + integer));
        mDisposables.add(disposable);
    }

    public void buffer(View view) {
        Disposable disposable = Observable.just(1, 2, 3, 4, 5, 6)
                .buffer(2, 3)
                .subscribe(integers -> Log.e(TAG, "accept: size = " + integers.size() + "item = " + integers.toString()));
        mDisposables.add(disposable);
    }

    public void timer(View view) {
        Disposable disposable = Observable.timer(1, TimeUnit.SECONDS)
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(aLong -> Log.e(TAG, "accept: 延时" + aLong + "秒打印"));
        mDisposables.add(disposable);
    }

    public void interval(View view) {
        Disposable disposable = Observable.interval(1, 2, TimeUnit.SECONDS)
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(aLong -> {
                    Log.e(TAG, "accept:" + "返回的是执行次数减去 1 ，along = " + aLong);
                });
        mDisposables.add(disposable);
    }

    public void doOnNext(View view) {
        Disposable disposable = Observable.just(1, 2, 3)
                .doOnNext(integer -> Log.e(TAG, "accept: " + "先保存一下" + integer))
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(integer -> Log.e(TAG, "accept: " + integer));
        mDisposables.add(disposable);
    }

    public void skip(View view) {
        Disposable disposable = Observable.just(1, 2, 3, 4, 5, 6, 7)
                .skip(6).compose(RxUtil.getDefaultScheduler())
                .subscribe(integer -> Log.e(TAG, "accept: after skip " + integer));
        mDisposables.add(disposable);
    }

    public void take(View view) {
        Disposable disposable = Observable.just(1, 2, 3, 4, 5, 6, 7)
                .take(3).compose(RxUtil.getDefaultScheduler())
                .subscribe(integer -> Log.e(TAG, "accept: take " + integer));
        mDisposables.add(disposable);
    }

    /**
     * 顾名思义，Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
     */
    public void single(View view) {
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.e(TAG, "onSuccess: " + "single " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * 去除发送频率过快的项
     */
    public void debounce(View view) {
        Disposable disposable = Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    // send events with simulated time wait
                    emitter.onNext(1); // skip
                    Thread.sleep(200);
                    emitter.onNext(2); // deliver
                    Thread.sleep(505);
                    emitter.onNext(3); // skip
                    Thread.sleep(100);
                    emitter.onNext(4); // deliver
                    Thread.sleep(605);
                    emitter.onNext(5); // deliver
                    Thread.sleep(510);
                    emitter.onComplete();
                })
                .debounce(500, TimeUnit.MILLISECONDS)
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(integer -> Log.e(TAG, "debounce :" + integer));
        mDisposables.add(disposable);
    }

    /**
     * 延缓订阅
     */
    public void defer(View view) {
        Observable<Integer> observable = Observable
                .defer((Callable<ObservableSource<Integer>>) () -> Observable.just(1, 2, 3));


        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "defer : " + integer + "\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "defer : onError : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "defer : onComplete\n");
            }
        });
    }

    /**
     * last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项
     */
    public void last(View view) {
        Disposable disposable = Observable.just(1, 2, 3, 4, 5, 6, 7)
                .last(3)
                .subscribe(integer -> Log.e(TAG, "last-3: " + integer));
        mDisposables.add(disposable);
    }

    public void merge(View view) {
        Disposable disposable = Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe(integer -> Log.e(TAG, "merge :" + integer + "\n"));
        mDisposables.add(disposable);
    }

    /**
     * 返回一个Maybe，它将指定的累加器函数应用于源ObservableSource发出的第一个项目，然后将该函数的结果与
     * 源ObservableSource发出的第二个项目一起提供给同一个函数，依此类推，直到所有项目都被发出由
     * 有限的源ObservableSource，并从最终调用函数发出最终结果作为其唯一项目。
     */
    public void reduce(View view) {
        Disposable disposable = Observable.just(2, 3, 4, 5)
                .reduce((integer, integer2) -> integer * integer2)
                .subscribe(integer -> Log.e(TAG, "accept: " + integer));
        mDisposables.add(disposable);
    }

    /**
     * 跟 reduce 类似，会打印每一步的结果
     */
    public void scan(View view) {
        Disposable disposable = Observable.just(2, 3, 4, 5)
                .scan((integer, integer2) -> {
                    Log.e(TAG, "apply: integer = " + integer + ",integer2 = " + integer2);
                    return integer * integer2;
                })
                .subscribe(integer -> Log.e(TAG, "accept: " + integer));
        mDisposables.add(disposable);
    }

    /**
     * 只有在在接收到 onNext 的改变时才触发订阅结果。打印结果 1 2 3
     */
    public void distinctUntilChanged(View view) {
        PublishSubject<Object> publishSubject = PublishSubject.create();
        Disposable disposable = publishSubject
                .distinctUntilChanged()
                .flatMapSingle((Function<Object, SingleSource<Boolean>>) o -> Single.create(emitter -> {
                    Log.e(TAG, "accept: " + o);
                    emitter.onSuccess(true);
                }))
                .subscribe(o -> {
                    Log.e(TAG, "accept: " + o);
                }, throwable -> {
                });
        mDisposables.add(disposable);
        publishSubject.onNext(1);
        publishSubject.onNext(1);
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);

        try {
            Class<?> clazz = Class.forName("com.song.sunset.utils.ScreenUtils");
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            Method dp2px = clazz.getMethod("dp2Px", Context.class, float.class);
            dp2px.setAccessible(true);
            float px = (float) dp2px.invoke(instance, this, 2f);
            Log.e(TAG, "反射：" + px);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
