package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

class FiveStartUp private constructor() {
    fun init(context: Context): FiveStartUp {
        Thread {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            Log.i("start_up_task", "5")
        }.start()
        return instance
    }

    companion object {
        val instance: FiveStartUp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FiveStartUp()
        }
    }
}