package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.OneStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/14 11:15
 */
class AInitializer : BaseInitializer<OneStartUp>() {
    override fun createStartUp(context: Context): OneStartUp {
        return OneStartUp.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(BInitializer::class.java)
    }
}