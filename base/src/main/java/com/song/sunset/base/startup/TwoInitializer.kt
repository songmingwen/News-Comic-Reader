package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.TwoStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:13
 */
class TwoInitializer : BaseInitializer<TwoStartUp>() {

    override fun createStartUp(context: Context): TwoStartUp {
        return TwoStartUp.instance.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(OneInitializer::class.java)
    }
}