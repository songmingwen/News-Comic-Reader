package com.song.sunset.comic.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.comic.startup.base.BaseInitializer
import com.song.sunset.comic.startup.task.FiveStartUp

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:29
 */
class FiveInitializer: BaseInitializer<FiveStartUp>() {

    override fun createInitializer(context: Context): FiveStartUp {
        FiveStartUp.getInstance().init(context)
        return FiveStartUp.getInstance()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}