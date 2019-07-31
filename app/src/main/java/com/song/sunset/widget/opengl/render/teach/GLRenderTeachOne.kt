package com.song.sunset.widget.opengl.render.teach

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.song.sunset.widget.opengl.air.objects.TeachObjectBuilder
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @author songmingwen
 * @description
 * @since 2019/7/26
 */

class GLRenderTeachOne(context: Context) : GLSurfaceView.Renderer {

    companion object {
        private const val BYTES_PRE_FLOAT = 4
    }

    private var mContext: Context = context

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

        //设置清空屏幕用的颜色
        glClearColor(0f, 0f, 0f, 0f)
        val buffer = ByteBuffer.allocateDirect(TeachObjectBuilder.getSourceArray().size * BYTES_PRE_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(TeachObjectBuilder.getSourceArray())

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //设置视口尺寸
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        //清空屏幕。屏幕会填充 glClearColor 中设置的颜色
        glClear(GL_COLOR_BUFFER_BIT)
    }

}