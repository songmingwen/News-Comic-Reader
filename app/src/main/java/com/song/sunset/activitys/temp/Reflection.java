package com.song.sunset.activitys.temp;

import android.util.Log;

/**
 * @author songmingwen
 * @description
 * @since 2019/2/20
 */
public class Reflection {

    private static final String TAG = "Reflection";

    /**
     * 公有成员变量
     */
    public float pubField;

    /**
     * 私有成员变量
     */
    private String priField;

    /**
     * 公有构造方法
     */
    public Reflection() {

    }

    /**
     * 私有构造方法
     */
    private Reflection(String priField, float pubField) {
        this.priField = priField;
        this.pubField = pubField;
    }

    /**
     * 公有无参函数
     */
    public void run() {
        Log.e(TAG, "run");
    }

    /**
     * 私有带参方法
     */
    private String getPriFiled(String extra) {
        return priField + "-------" + extra;
    }

    @Override
    public String toString() {
        return pubField + "-------" + priField;
    }
}
