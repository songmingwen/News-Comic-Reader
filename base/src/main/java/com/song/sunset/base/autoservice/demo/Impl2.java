package com.song.sunset.base.autoservice.demo;

import android.util.Log;

import com.google.auto.service.AutoService;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/13
 */
@AutoService(Interface.class)
public class Impl2 implements Interface {
    @Override
    public String getString() {
        return "Impl2";
    }

    @Override
    public void doSomething(String data) {
        Log.i(TAG, "Impl2 接收参数，执行自己的任务，data=" + data);
    }
}
