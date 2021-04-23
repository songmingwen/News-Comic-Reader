package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.FourStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:26
 */
class FourInitializer: BaseInitializer<FourStartUp>() {

    override fun createStartUp(context: Context): FourStartUp {
        return FourStartUp.instance.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(OneInitializer::class.java, ThreeInitializer::class.java)
    }
}