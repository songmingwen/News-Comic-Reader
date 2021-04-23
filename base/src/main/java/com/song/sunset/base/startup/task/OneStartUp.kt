package com.song.sunset.base.startup.task

import android.content.Context
import android.util.Log

class OneStartUp private constructor() {
    fun init(context: Context?):OneStartUp {
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("start_up_task", "1")
        return instance
    }

    companion object {
        val instance: OneStartUp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OneStartUp()
        }
    }
}