package com.song.sunset.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tencent.mmkv.MMKV;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.song.sunset.utils.SPUtils.NO_SP_MODE;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/619:37
 */
public class RelayTest {

    public static final int COUNT = 1000;

    public static void testRelay() {
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

    }

    public static void testMMKV() {
        //先清理
        SharedPreferences clear = AppConfig.getApp().getSharedPreferences("test", Context.MODE_PRIVATE);
        clear.edit().clear().apply();

        //设置 sp
        SPUtils.setBooleanByName(AppConfig.getApp(), "test", NO_SP_MODE, "boolean", true);
        SPUtils.setIntByName(AppConfig.getApp(), "test", NO_SP_MODE, "int", 10086);
        SPUtils.setLongByName(AppConfig.getApp(), "test", NO_SP_MODE, "long", 10010L);
        SPUtils.setStringByName(AppConfig.getApp(), "test", NO_SP_MODE, "string", "sp");
        SPUtils.setFloatByName(AppConfig.getApp(), "test", NO_SP_MODE, "float", 100.11F);

        boolean bool = SPUtils.getBooleanByName(AppConfig.getApp(), "test", NO_SP_MODE, "boolean", false);
        int in = SPUtils.getIntByName(AppConfig.getApp(), "test", NO_SP_MODE, "int", 0);
        long lon = SPUtils.getLongByName(AppConfig.getApp(), "test", NO_SP_MODE, "long", 0);
        String string = SPUtils.getStringByName(AppConfig.getApp(), "test", NO_SP_MODE, "string", "no");
        float fl = SPUtils.getFloatByName(AppConfig.getApp(), "test", NO_SP_MODE, "float", 1.0f);

        //先清理 mmkv
        MMKV mmkv = MMKV.mmkvWithID("test");
        mmkv.clearAll();

        //转移数据到 mmkv
        SharedPreferences old = AppConfig.getApp().getSharedPreferences("test", Context.MODE_PRIVATE);
        mmkv.importFromSharedPreferences(old);

        boolean mBool = mmkv.getBoolean("boolean", false);
        int mIn = mmkv.getInt("int", 0);
        long mLon = mmkv.getLong("long", 0);
        String mString = mmkv.getString("string", "no");
        float mFl = mmkv.getFloat("float", 0);

        Log.d("testMMKV-bool", (bool == mBool) + "");
        Log.d("testMMKV-int", (in == mIn) + "");
        Log.d("testMMKV-long", (lon == mLon) + "");
        Log.d("testMMKV-string", (string.equals(mString)) + "");
        Log.d("testMMKV-float", (fl == mFl) + "");


        Disposable disposable =
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    emitter.onNext(testBigNum());
                }).subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            Log.d("testMMKV", s);
                        });
    }

    private static String testBigNum() {
        //先清理
        SharedPreferences clear = AppConfig.getApp().getSharedPreferences("test", Context.MODE_PRIVATE);
        clear.edit().clear().apply();
        MMKV mmkv = MMKV.mmkvWithID("test");
        mmkv.clearAll();

        for (int i = 0; i < COUNT; i++) {
            SPUtils.setIntByName(AppConfig.getApp(), "test", NO_SP_MODE, "int" + i, i);
        }

        //转移数据到 mmkv
        SharedPreferences old = AppConfig.getApp().getSharedPreferences("test", Context.MODE_PRIVATE);
        mmkv.importFromSharedPreferences(old);

        Log.d("testMMKV", "length=" + mmkv.allKeys().length);
        for (int i = 0; i < COUNT; i++) {
            Log.d("testMMKV", mmkv.getInt("int" + i, 0) + "");
        }

        return "done";
    }
}
