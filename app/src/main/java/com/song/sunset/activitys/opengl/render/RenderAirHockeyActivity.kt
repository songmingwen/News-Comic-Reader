package com.song.sunset.activitys.opengl.render

import android.view.View
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceViewAirHockey

class RenderAirHockeyActivity : BaseRenderActivity() {

    override fun getGLSurfaceView(): View {
        return GLSurfaceViewAirHockey(this)
    }

}
