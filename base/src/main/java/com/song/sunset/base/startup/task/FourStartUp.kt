package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

object FourStartUp {

    fun init(context: Context): FourStartUp {
        try {
            Thread.sleep(400)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "4")
        return FourStartUp
    }

}