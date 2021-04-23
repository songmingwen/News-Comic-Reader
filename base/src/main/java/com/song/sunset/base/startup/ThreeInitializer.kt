package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.ThreeStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:25
 */
class ThreeInitializer : BaseInitializer<ThreeStartUp>() {

    override fun createStartUp(context: Context): ThreeStartUp {
        return ThreeStartUp.instance.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TwoInitializer::class.java)
    }

}