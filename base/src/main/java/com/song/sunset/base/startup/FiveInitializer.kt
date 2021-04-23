package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.FiveStartUp
import java.util.Collections.emptyList

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/7 17:29
 */
class FiveInitializer: BaseInitializer<FiveStartUp>() {

    override fun createStartUp(context: Context): FiveStartUp {
        return FiveStartUp.instance.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}