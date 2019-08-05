package com.song.sunset.widget.opengl.render.teach

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.song.sunset.R
import com.song.sunset.widget.opengl.air.objects.TeachObjectBuilder
import com.song.sunset.widget.opengl.utils.ShaderHelper
import com.song.sunset.widget.opengl.utils.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @author songmingwen
 * @description
 * @since 2019/8/2
 */

class GLRenderTeachTwo(context: Context) : GLSurfaceView.Renderer {

    companion object {
        private const val BYTES_PRE_FLOAT = 4

        private const val POINT_SIZE = 2
        private val COLOR_SIZE = 3

        private const val A_POSITION = "a_Position"
        private const val U_COLOR = "u_Color"
    }

    private var mPositionIndex: Int = 0
    private var mColorIndex: Int = 0

    private val mSourceArray = floatArrayOf(
            //两个三角
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,
            //线段
            -0.5f, 0f,
            0.5f, 0f,
            //两个点
            0f, 0.25f,
            0f, -0.25f
    )

    private var mContext: Context = context

    private var mProgramId: Int = 0

    private lateinit var buffer: FloatBuffer

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 0f)
        buffer = ByteBuffer.allocateDirect(mSourceArray.size * BYTES_PRE_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mSourceArray)

        val vertexShader = ShaderHelper.compileVertexShader(TextResourceReader.readTextFileFromResource(mContext, R.raw.vertex_shader_teach_two))
        val fragmentShader = ShaderHelper.compileFragmentShader(TextResourceReader.readTextFileFromResource(mContext, R.raw.fragment_shader_teach_two))
        mProgramId = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        glUseProgram(mProgramId)

        mPositionIndex = glGetAttribLocation(mProgramId, A_POSITION)
        mColorIndex = glGetUniformLocation(mProgramId, U_COLOR)

        buffer.position(0)
        glVertexAttribPointer(mPositionIndex, POINT_SIZE, GL_FLOAT, false, 0, buffer)
        glEnableVertexAttribArray(mPositionIndex)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        //绘制三角形
        glUniform4f(mColorIndex, 1.0f, 1.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        //绘制线段
        glUniform4f(mColorIndex, 0f, 0f, 1.0f, 1.0f)
        glDrawArrays(GL_LINES, 6, 2)
        //绘制点
        glUniform4f(mColorIndex, 0f, 0f, 0f, 1.0f)
        glDrawArrays(GL_POINTS, 8, 2)
    }

}