package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

object ThreeStartUp {

    fun init(context: Context): ThreeStartUp {
        try {
            Thread.sleep(300)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "3")
        return ThreeStartUp
    }

}