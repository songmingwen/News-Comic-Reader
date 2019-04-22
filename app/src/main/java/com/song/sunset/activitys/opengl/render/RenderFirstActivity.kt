package com.song.sunset.activitys.opengl.render

import android.view.View
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceViewFirst

class RenderFirstActivity : BaseRenderActivity() {

    override fun getGLSurfaceView(): View {
        return GLSurfaceViewFirst(this)
    }
}
