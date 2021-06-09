package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

object TwoStartUp {

    fun init(context: Context): TwoStartUp {
        try {
            Thread.sleep(200)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "2")
        return TwoStartUp
    }

}