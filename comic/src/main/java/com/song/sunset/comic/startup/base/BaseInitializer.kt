package com.song.sunset.comic.startup.base

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

/**
 * Desc:    Initializer 基类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:47
 */
abstract class BaseInitializer<T> : Initializer<T> {

    override fun create(context: Context): T {
        val start: Long = System.currentTimeMillis()
        val initializer = createInitializer(context)
        val end: Long = System.currentTimeMillis()
        Log.i("start_up_task", "Initializer name=" + javaClass.simpleName + "，time=" + (end - start))
        return initializer
    }

    abstract fun createInitializer(context: Context): T
}