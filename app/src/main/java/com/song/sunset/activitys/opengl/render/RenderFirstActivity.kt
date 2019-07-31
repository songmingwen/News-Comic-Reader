package com.song.sunset.activitys.opengl.render

import com.song.sunset.widget.opengl.render.GLRenderFirst
import com.song.sunset.widget.opengl.surfaceview.BaseGLSurfaceView

class RenderFirstActivity : BaseRenderActivity() {

    override fun getGLSurfaceView(): BaseGLSurfaceView {
        return object : BaseGLSurfaceView(this) {
            override fun getRender(): Renderer {
                return GLRenderFirst(context)
            }
        }
    }
}
