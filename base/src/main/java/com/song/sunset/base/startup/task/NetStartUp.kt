package com.song.sunset.base.startup.task

import android.content.Context
import com.song.sunset.base.net.OkHttpClient

object NetStartUp {

    fun init(context: Context?): NetStartUp {
        OkHttpClient.obtainClient()
        return NetStartUp
    }

}