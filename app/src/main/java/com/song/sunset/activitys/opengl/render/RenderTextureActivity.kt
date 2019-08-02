package com.song.sunset.activitys.opengl.render

import com.song.sunset.widget.opengl.render.GLRenderTextured
import com.song.sunset.widget.opengl.surfaceview.BaseGLSurfaceView

class RenderTextureActivity : BaseRenderActivity() {

    override fun getGLSurfaceView(): BaseGLSurfaceView {
        return object : BaseGLSurfaceView(this) {
            override val render: Renderer
                get() = GLRenderTextured(context)
        }
    }
}
