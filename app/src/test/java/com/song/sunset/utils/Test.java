package com.song.sunset.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Song
 */
public class Test {

    @org.junit.Test
    public void test() {
        System.out.println("need = " + need("mgtv1234"));
        System.out.println("need = " + need("mg12345"));
        System.out.println("need = " + need("mg1234567"));
        System.out.println("need = " + need("mg123456789012"));
        System.out.println("need = " + need("mg12345678abc"));
        System.out.println("need = " + need("mg12345678abcde"));

        System.out.println("need = " + need("mg123456"));
        System.out.println("need = " + need("mg12345678aBxZ"));
        System.out.println("need = " + need("mg1234567890"));

    }

    /**
     * 是否是手机号
     */
    public static boolean isMobile(String mobile) {
        String regex = "^(1)\\d{10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean need(String mobile) {
        String regex = "^(mg)\\d{6}$|^(mg)\\d{10}$|^(mg)\\d{8}[a-zA-Z]{4}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

//    public static boolean need(String mobile) {
//        String regex1 = "^(mg)\\d{6}$";
//        String regex2 = "^(mg)\\d{10}$";
//        String regex3 = "^(mg)\\d{8}[a-zA-Z]{4}$";
//        Pattern p1 = Pattern.compile(regex1);
//        Pattern p2 = Pattern.compile(regex2);
//        Pattern p3 = Pattern.compile(regex3);
//        Matcher m1 = p1.matcher(mobile);
//        Matcher m2 = p2.matcher(mobile);
//        Matcher m3 = p3.matcher(mobile);
//        return m1.matches() || m2.matches() || m3.matches();
//    }

    /**
     * 是否是手机号
     */
//    public static boolean isMobile(String mobile) {
//        String regex = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(mobile);
//        return m.matches();
//    }

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