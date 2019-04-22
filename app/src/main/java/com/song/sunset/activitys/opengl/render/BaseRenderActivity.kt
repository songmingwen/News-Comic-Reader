package com.song.sunset.activitys.opengl.render

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.song.sunset.activitys.base.BaseActivity
import com.song.sunset.widget.opengl.surfaceview.BaseGLSurfaceView

abstract class BaseRenderActivity : BaseActivity() {

    companion object {
        fun start(context: Context, cls: Class<*>) {
            context.startActivity(Intent(context, cls))
        }
    }

    private var glSurfaceView: BaseGLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getGLSurfaceView())
    }

    abstract fun getGLSurfaceView(): View

    override fun onResume() {
        super.onResume()
        glSurfaceView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView?.onPause()
    }
}
