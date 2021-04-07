package com.song.sunset.comic.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.comic.startup.base.BaseInitializer
import com.song.sunset.comic.startup.task.FourStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:26
 */
class FourInitializer: BaseInitializer<FourStartUp>() {

    override fun createInitializer(context: Context): FourStartUp {
        FourStartUp.getInstance().init(context)
        return FourStartUp.getInstance()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(OneInitializer::class.java, ThreeInitializer::class.java)
    }
}