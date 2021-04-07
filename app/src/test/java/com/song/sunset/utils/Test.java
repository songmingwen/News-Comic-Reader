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


    }
}