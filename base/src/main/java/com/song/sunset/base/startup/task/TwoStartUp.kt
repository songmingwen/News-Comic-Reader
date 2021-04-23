package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

class TwoStartUp private constructor() {
    fun init(context: Context): TwoStartUp {
        try {
            Thread.sleep(200)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "2")
        return instance
    }

    companion object {
        val instance: TwoStartUp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TwoStartUp()
        }
    }
}