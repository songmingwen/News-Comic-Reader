package com.song.sunset.utils;

import android.net.Uri;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

        Relay<Integer> relay = Relay.getInstance("song");

        Observable<Integer> observable = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());

        relay.accept(observable);

        Observable<Integer> observable1 = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(3);
        }).subscribeOn(Schedulers.io());

        relay.end(observable1).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer o) {
                System.out.println(o.toString());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        });

    }
}