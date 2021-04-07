package com.song.sunset.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright(C),2006-2021,快乐阳光互动娱乐传媒有限公司
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/619:37
 */
public class RelayTest {

    public static void testRelay(){
        Relay<Integer> relay = Relay.getInstance("song");

        Observable<Integer> accept1 = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onComplete();//前面的 Observer 没有发送任何数据就 onComplete 或者 onError 的时候，后面的 Observer 才会执行
            emitter.onNext(1);
        }).subscribeOn(Schedulers.io());
        relay.accept(accept1);

        Observable<Integer> accept2 = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onError(null);//前面的 Observer 没有发送任何数据就 onComplete 或者 onError 的时候，后面的 Observer 才会执行
            emitter.onNext(2);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
        relay.accept(accept2);

        Observable<Integer> endObservable = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(3);
        }).subscribeOn(Schedulers.io());
        Disposable disposable = relay.end(endObservable).subscribe(integer -> {
            System.out.println("onNext:" + integer.toString());
        }, throwable -> {

        });

//        new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer o) {
//                System.out.println("onNext" + o.toString());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println(e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("complete");
//            }
//        }

    }
}
