package com.song.sunset.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.song.sunset.base.startup.base.BaseInitializer
import com.song.sunset.base.startup.task.TwoStartUp

class BInitializer : BaseInitializer<TwoStartUp>() {
    override fun createStartUp(context: Context): TwoStartUp {
        return TwoStartUp.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(AInitializer::class.java)
    }

}
