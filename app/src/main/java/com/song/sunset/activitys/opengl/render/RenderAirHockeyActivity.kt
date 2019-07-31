package com.song.sunset.activitys.opengl.render

import com.song.sunset.widget.opengl.surfaceview.BaseGLSurfaceView
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceViewAirHockey

class RenderAirHockeyActivity : BaseRenderActivity() {

    override fun getGLSurfaceView(): BaseGLSurfaceView {
        return GLSurfaceViewAirHockey(this)
    }

}
