package com.song.sunset.activitys.opengl.teach

import com.song.sunset.activitys.opengl.render.BaseRenderActivity
import com.song.sunset.widget.opengl.render.teach.GLRenderTeachTwo
import com.song.sunset.widget.opengl.surfaceview.BaseGLSurfaceView

/**
 * @author songmingwen
 * @description
 * @since 2019/8/2
 */

class TeachTwoActivity : BaseRenderActivity() {
    override fun getGLSurfaceView(): BaseGLSurfaceView {
        return object : BaseGLSurfaceView(this) {
            override val render: Renderer
                get() = GLRenderTeachTwo(context)
        }
    }
}