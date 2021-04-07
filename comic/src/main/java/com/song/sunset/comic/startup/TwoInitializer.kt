package com.song.sunset.comic.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.comic.startup.base.BaseInitializer
import com.song.sunset.comic.startup.task.TwoStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:13
 */
class TwoInitializer : BaseInitializer<TwoStartUp>() {

    override fun createInitializer(context: Context): TwoStartUp {
        TwoStartUp.getInstance().init(context)
        return TwoStartUp.getInstance()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(OneInitializer::class.java)
    }
}