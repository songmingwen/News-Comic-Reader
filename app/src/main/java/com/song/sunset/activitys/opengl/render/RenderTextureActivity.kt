package com.song.sunset.activitys.opengl.render

import android.content.Context
import android.content.Intent
import android.view.View
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceViewTextured

class RenderTextureActivity : BaseRenderActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RenderTextureActivity::class.java))
        }
    }

    override fun getGLSurfaceView(): View {
        return GLSurfaceViewTextured(this)
    }
}
