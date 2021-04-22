package com.song.sunset.utils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Song
 */
public class Test {

    @org.junit.Test
    public void test() {
        testRelay();
    }

    @org.junit.Test
    public void testList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add(3, "4");
        System.out.println(list.toArray().toString());
    }

    private void testRelay() {

        Observable<String> success = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("success");
            emitter.onComplete();//前面的 Observer 没有发送任何数据就 onComplete 或者 onError 的时候，后面的 Observer 才会执行
        }).subscribeOn(Schedulers.io());


        Observable<String> failure = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onError(null);//前面的 Observer 没有发送任何数据就 onComplete 或者 onError 的时候，后面的 Observer 才会执行
            emitter.onNext("ignore");
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());


        //测试接力第一棒成功
        Relay<String> relay = Relay.getInstance("song");
        relay.accept(success);
        relay.accept(failure);
        Observable<String> endObservable = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("3");
        }).subscribeOn(Schedulers.io());
        Disposable disposable = relay.end(endObservable).subscribe(string -> {
            System.out.println("onNext:" + string);
        }, throwable -> {

        });

        //测试接力非第一棒成功

        //测试接力兜底帮成功
    }
}