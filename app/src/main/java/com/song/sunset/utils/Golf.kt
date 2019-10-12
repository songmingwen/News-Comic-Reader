package com.song.sunset.utils

import android.content.Context
import android.util.Log
import com.song.sunset.interfaces.ICar

/**
 * @author songmingwen
 * @description
 * @since 2019/3/5
 */
class Golf : ICar {

    override fun wheel() {
        Log.e(this.javaClass.toString(), "安装轮胎")
    }

    override fun engine() {
        Log.e(this.javaClass.toString(), "安装发动机")
    }

    override fun level() {
        Log.e(this.javaClass.toString(), "汽车级别")
    }

    override fun timeToMarket(context: Context) {
        Log.e(this.javaClass.toString(), "上市时间")
    }

}