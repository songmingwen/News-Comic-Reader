package com.song.sunset.activitys.opengl.render

import android.view.View
import com.song.sunset.widget.opengl.surfaceview.GLSurfaceView3D

class Render3DActivity : BaseRenderActivity() {

    override fun getGLSurfaceView(): View {
        return GLSurfaceView3D(this)
    }

}
