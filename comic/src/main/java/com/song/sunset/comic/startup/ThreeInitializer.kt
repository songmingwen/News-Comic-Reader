package com.song.sunset.comic.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.comic.startup.base.BaseInitializer
import com.song.sunset.comic.startup.task.FourStartUp
import com.song.sunset.comic.startup.task.ThreeStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:25
 */
class ThreeInitializer : BaseInitializer<ThreeStartUp>() {

    override fun createInitializer(context: Context): ThreeStartUp {
        ThreeStartUp.getInstance().init(context)
        return ThreeStartUp.getInstance()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TwoInitializer::class.java)
    }

}