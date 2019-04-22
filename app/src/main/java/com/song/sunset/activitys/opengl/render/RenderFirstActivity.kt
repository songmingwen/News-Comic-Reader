package com.song.sunset.activitys.opengl.render

import android.content.Context
import android.content.Intent
import android.view.View
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceViewFirst

class RenderFirstActivity : BaseRenderActivity() {

    companion object {
        fun start(context: Context){
            context.startActivity(Intent(context, RenderFirstActivity::class.java))

        }
    }

    override fun getGLSurfaceView(): View {
        return GLSurfaceViewFirst(this)
    }
}
