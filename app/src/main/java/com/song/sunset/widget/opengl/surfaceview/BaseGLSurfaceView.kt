package com.song.sunset.widget.opengl.surfaceview

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * @author songmingwen
 * @description
 * @since 2019/4/18
 */
abstract class BaseGLSurfaceView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : GLSurfaceView(context, attrs) {

    protected abstract val render: GLSurfaceView.Renderer

    init {
        init(context)
    }

    private fun init(context: Context) {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val supportEs2 = configurationInfo.reqGlEsVersion >= 0x20000
        if (supportEs2) {
            setEGLContextClientVersion(2)
            setRenderer(render)
            //             只有当绘制数据变化时，才绘制视图。
            //            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }
}
