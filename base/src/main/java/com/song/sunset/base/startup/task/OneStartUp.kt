package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

object OneStartUp {

    fun init(context: Context?): OneStartUp {
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "1")
        return OneStartUp
    }

}