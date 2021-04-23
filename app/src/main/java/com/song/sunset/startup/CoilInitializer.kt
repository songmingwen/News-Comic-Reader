package com.song.sunset.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.startup.task.CoilStartUp

/**
 * Copyright(C),2006-2021,快乐阳光互动娱乐传媒有限公司
 * Desc: 初始化 Coil 库任务
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/23 9:37
 */
class CoilInitializer: BaseInitializer<CoilStartUp>() {
    override fun createStartUp(context: Context): CoilStartUp {
        return CoilStartUp.instance.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}