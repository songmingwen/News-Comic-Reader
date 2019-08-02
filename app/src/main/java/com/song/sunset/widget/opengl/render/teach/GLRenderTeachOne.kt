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

    //当 Surface 被创建的时候 GLSurfaceView 会调用此方法；程序初次运行或者切换 activity 时都可能触发此方法
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //设置清空屏幕用的颜色
        glClearColor(0f, 0f, 1f, 1f)

    }

    //每次 Surface 尺寸发生变化时，此方法会被 GLSurfaceView 调用；横竖屏切换时，Surface 尺寸会变化
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //设置视口尺寸
        glViewport(0, 0, width, height)
    }

    //当绘制一帧时，此方法会被 GLSurfaceView 调用
    override fun onDrawFrame(gl: GL10?) {
        //清空屏幕。屏幕会填充 glClearColor 中设置的颜色
        glClear(GL_COLOR_BUFFER_BIT)
    }

}