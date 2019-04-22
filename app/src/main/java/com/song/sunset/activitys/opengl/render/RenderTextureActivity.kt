package com.song.sunset.activitys.opengl.render

import android.view.View
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceViewTextured

class RenderTextureActivity : BaseRenderActivity() {

    override fun getGLSurfaceView(): View {
        return GLSurfaceViewTextured(this)
    }
}
