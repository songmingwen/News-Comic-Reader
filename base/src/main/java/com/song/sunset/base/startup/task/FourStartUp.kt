package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

class FourStartUp private constructor() {
    fun init(context: Context): FourStartUp {
        try {
            Thread.sleep(400)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "4")
        return instance
    }

    companion object {
        val instance: FourStartUp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FourStartUp()
        }
    }
}