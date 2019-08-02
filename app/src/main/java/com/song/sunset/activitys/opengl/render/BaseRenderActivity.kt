package com.song.sunset.activitys.opengl.render

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.song.sunset.activitys.base.BaseActivity
import com.song.sunset.widget.opengl.surfaceview.BaseGLSurfaceView

abstract class BaseRenderActivity : BaseActivity() {

    companion object {
        fun start(context: Context, cls: Class<*>) {
            context.startActivity(Intent(context, cls))
        }
    }

    private lateinit var glSurfaceView: BaseGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView = getGLSurfaceView()
        setContentView(glSurfaceView)
    }

    abstract fun getGLSurfaceView(): BaseGLSurfaceView

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }
}
