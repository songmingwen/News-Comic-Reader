package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

class ThreeStartUp private constructor() {
    fun init(context: Context): ThreeStartUp {
        try {
            Thread.sleep(300)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "3")
        return instance
    }

    companion object {
        val instance: ThreeStartUp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ThreeStartUp()
        }
    }
}