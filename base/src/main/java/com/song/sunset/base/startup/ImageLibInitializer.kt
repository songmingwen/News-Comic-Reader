package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.FrescoInitializer
import java.util.Collections.emptyList

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 16:49
 */
class ImageLibInitializer : BaseInitializer<FrescoInitializer>() {

    override fun createStartUp(context: Context): FrescoInitializer {
        val frescoInitializer = FrescoInitializer.getDefaultInstance()
        frescoInitializer.initialize(context)
        return frescoInitializer
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}