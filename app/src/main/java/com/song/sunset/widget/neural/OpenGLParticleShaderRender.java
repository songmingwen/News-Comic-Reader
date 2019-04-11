package com.song.sunset.widget.neural;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.song.sunset.utils.JsonUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/11
 */
public class OpenGLParticleShaderRender implements GLSurfaceView.Renderer {

    //定点着色器
    public static String VL = "attribute vec4 vPosition;\n" +
            "uniform mat4 u_Matrix;"
            + "void main() {\n"
            + "  gl_Position = u_Matrix*vPosition;\n"
            + "}";
    //片段着色器
    public static String FL = "precision mediump float;\n" +
            "uniform vec4 u_Color;"
            + "void main() {\n"
            + "  gl_FragColor = u_Color;\n"
            + "}";

    private FloatBuffer verticalsBuffer;

    private static final int AMOUNTS = 360;
    private static final int CIRCLE_ANGLE = 360;
    private static final int PI_ANGLE = 180;
    private static final double ANGLE = 360d / AMOUNTS;
    private float[] verticals = new float[AMOUNTS * 3 * 3];

    private float[] projectMatrix = new float[16];

    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMatricHandle;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        float x = 0;
        float y = 0;
        float z = 0;
        float r = 1f;
        int index = -1;
        for (double i = ANGLE; i <= CIRCLE_ANGLE; i = i + ANGLE) {
            //三角形的三个点
            double d1 = i * Math.PI / PI_ANGLE;
            double d2 = (i - ANGLE) * Math.PI / PI_ANGLE;
            //第一个点 x、y、z 的值
            verticals[++index] = 0;
            verticals[++index] = 0;
            verticals[++index] = 0;

            //第二个点 x、y、z 的值
            verticals[++index] = (float) (x + r * Math.cos(d2));
            verticals[++index] = (float) (y + r * Math.sin(d2));
            verticals[++index] = 0;

            //第三个点 x、y、z 的值
            verticals[++index] = (float) (x + r * Math.cos(d1));
            verticals[++index] = (float) (y + r * Math.sin(d1));
            verticals[++index] = 0;
        }
        verticalsBuffer = ByteBuffer.allocateDirect(verticals.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(verticals);
        verticalsBuffer.position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mProgram = GLES20.glCreateProgram();

        int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShader, VL);
        GLES20.glCompileShader(vertexShader);

        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader, FL);
        GLES20.glCompileShader(fragmentShader);

        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "u_Color");
        mMatricHandle = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
        //处理变形
        final float aspectRatio = width > height ? width * 1f / height : height * 1f / width;
        if (width > height) {
            Matrix.orthoM(projectMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            Matrix.orthoM(projectMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 12, verticalsBuffer);
        GLES20.glUniform4fv(mColorHandle, 1, new float[]{0, 1, 1, 5}, 0);
        GLES20.glUniformMatrix4fv(mMatricHandle, 1, false, projectMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, AMOUNTS * 3);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
