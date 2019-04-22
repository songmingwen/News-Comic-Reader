package com.song.sunset.activitys.opengl.render

import android.content.Context
import android.content.Intent
import android.view.View
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceView3D

class Render3DActivity : BaseRenderActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, Render3DActivity::class.java))
        }
    }

    override fun getGLSurfaceView(): View {
        return GLSurfaceView3D(this)
    }

}
