package com.song.sunset.activitys.opengl.teach

import com.song.sunset.activitys.opengl.render.BaseRenderActivity
import com.song.sunset.widget.opengl.render.teach.GLRenderTeachOne
import com.song.sunset.widget.opengl.surfaceview.BaseGLSurfaceView

/**
 * @author songmingwen
 * @description
 * @since 2019/7/31
 */
class TeachOneActivity : BaseRenderActivity() {
    override fun getGLSurfaceView(): BaseGLSurfaceView {
        return object : BaseGLSurfaceView(this) {
            override val render: Renderer
                get() = GLRenderTeachOne(context)
        }
    }
}