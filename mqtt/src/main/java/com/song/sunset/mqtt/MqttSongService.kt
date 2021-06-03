package com.song.sunset.mqtt

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/31 10:07
 */
class MqttSongService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}