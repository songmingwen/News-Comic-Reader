package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.NetStartUp
import java.util.Collections.emptyList

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 16:49
 */
class NetInitializer : BaseInitializer<NetStartUp>() {

    override fun createStartUp(context: Context): NetStartUp {
        return NetStartUp.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}