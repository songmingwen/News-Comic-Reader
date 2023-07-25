//================================================================================================================================
//
// Copyright (c) 2015-2022 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.easyar.samples.arvideo;

import android.opengl.GLES30;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

import cn.easyar.Matrix44F;
import cn.easyar.Vec2F;

public class VideoRenderer {

    private int proGramBox;
    private int posCoordBox;
    private int posTexBox;
    private int posTransBox;
    private int posProjBox;
    private int vboCoordBox;
    private int vboTexBox;
    private int vboFacesBox;
    private int textureId;

    private EGLContext currentContext = null;

    private String box_vert = "uniform mat4 trans;\n"
            + "uniform mat4 proj;\n"
            + "attribute vec4 coord;\n"
            + "attribute vec2 texcoord;\n"
            + "varying vec2 vtexcoord;\n"
            + "\n"
            + "void main(void)\n"
            + "{\n"
            + "    vtexcoord = texcoord;\n"
            + "    gl_Position = proj*trans*coord;\n"
            + "}\n"
            + "\n";

    private String box_frag = "#ifdef GL_ES\n"
            + "precision highp float;\n"
            + "#endif\n"
            + "varying vec2 vtexcoord;\n"
            + "uniform sampler2D texture;\n"
            + "\n"
            + "void main(void)\n"
            + "{\n"
            + "    gl_FragColor = texture2D(texture, vtexcoord);\n"
            + "}\n"
            + "\n";

    private float[] flatten(float[][] a) {
        int size = 0;
        for (int k = 0; k < a.length; k += 1) {
            size += a[k].length;
        }
        float[] l = new float[size];
        int offset = 0;
        for (int k = 0; k < a.length; k += 1) {
            System.arraycopy(a[k], 0, l, offset, a[k].length);
            offset += a[k].length;
        }
        return l;
    }

    private int[] flatten(int[][] a) {
        int size = 0;
        for (int k = 0; k < a.length; k += 1) {
            size += a[k].length;
        }
        int[] l = new int[size];
        int offset = 0;
        for (int k = 0; k < a.length; k += 1) {
            System.arraycopy(a[k], 0, l, offset, a[k].length);
            offset += a[k].length;
        }
        return l;
    }

    private short[] flatten(short[][] a) {
        int size = 0;
        for (int k = 0; k < a.length; k += 1) {
            size += a[k].length;
        }
        short[] l = new short[size];
        int offset = 0;
        for (int k = 0; k < a.length; k += 1) {
            System.arraycopy(a[k], 0, l, offset, a[k].length);
            offset += a[k].length;
        }
        return l;
    }

    private byte[] flatten(byte[][] a) {
        int size = 0;
        for (int k = 0; k < a.length; k += 1) {
            size += a[k].length;
        }
        byte[] l = new byte[size];
        int offset = 0;
        for (int k = 0; k < a.length; k += 1) {
            System.arraycopy(a[k], 0, l, offset, a[k].length);
            offset += a[k].length;
        }
        return l;
    }

    private byte[] byteArrayFromIntArray(int[] a) {
        byte[] l = new byte[a.length];
        for (int k = 0; k < a.length; k += 1) {
            l[k] = (byte) (a[k] & 0xFF);
        }
        return l;
    }

    private int generateOneBuffer() {
        int[] buffer = {0};
        GLES30.glGenBuffers(1, buffer, 0);
        return buffer[0];
    }

    private int generateOneTexture() {
        int[] buffer = {0};
        GLES30.glGenTextures(1, buffer, 0);
        return buffer[0];
    }

    private static float[] getGLMatrix(Matrix44F m) {
        float[] d = m.data;
        return new float[]{
                d[0], d[4], d[8], d[12],
                d[1], d[5], d[9], d[13],
                d[2], d[6], d[10], d[14],
                d[3], d[7], d[11], d[15]};
    }

    public VideoRenderer() {
        currentContext = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();

        proGramBox = GLES30.glCreateProgram();
        int vertShader = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);
        GLES30.glShaderSource(vertShader, box_vert);
        GLES30.glCompileShader(vertShader);
        int fragShader = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
        GLES30.glShaderSource(fragShader, box_frag);
        GLES30.glCompileShader(fragShader);
        GLES30.glAttachShader(proGramBox, vertShader);
        GLES30.glAttachShader(proGramBox, fragShader);
        GLES30.glLinkProgram(proGramBox);
        GLES30.glUseProgram(proGramBox);
        GLES30.glDeleteShader(vertShader);
        GLES30.glDeleteShader(fragShader);
        posCoordBox = GLES30.glGetAttribLocation(proGramBox, "coord");
        posTexBox = GLES30.glGetAttribLocation(proGramBox, "texcoord");
        posTransBox = GLES30.glGetUniformLocation(proGramBox, "trans");
        posProjBox = GLES30.glGetUniformLocation(proGramBox, "proj");

        vboCoordBox = generateOneBuffer();
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboCoordBox);
        float[][] cube_vertices = {
                {1.0f / 2, 1.0f / 2, 0.f},
                {1.0f / 2, -1.0f / 2, 0.f},
                {-1.0f / 2, -1.0f / 2, 0.f},
                {-1.0f / 2, 1.0f / 2, 0.f}};
        FloatBuffer cubeVerticesBuffer = FloatBuffer.wrap(flatten(cube_vertices));
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, cubeVerticesBuffer.limit() * 4, cubeVerticesBuffer, GLES30.GL_DYNAMIC_DRAW);

        vboTexBox = generateOneBuffer();
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboTexBox);
        int[][] cubeVertexTexs = {
                {1, 0},
                {1, 1},
                {0, 1},
                {0, 0}};
        ByteBuffer cube_vertex_texs_buffer = ByteBuffer.wrap(byteArrayFromIntArray(flatten(cubeVertexTexs)));
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, cube_vertex_texs_buffer.limit(), cube_vertex_texs_buffer, GLES30.GL_STATIC_DRAW);

        vboFacesBox = generateOneBuffer();
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboFacesBox);
        short[] cube_faces = {3, 2, 1, 0};
        ShortBuffer cube_faces_buffer = ShortBuffer.wrap(cube_faces);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, cube_faces_buffer.limit() * 2, cube_faces_buffer, GLES30.GL_STATIC_DRAW);

        GLES30.glUniform1i(GLES30.glGetUniformLocation(proGramBox, "texture"), 0);
        textureId = generateOneTexture();
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
    }

    public void dispose() {
        if (((EGL10) EGLContext.getEGL()).eglGetCurrentContext().equals(currentContext)) { //destroy resources unless the context has lost
            GLES30.glDeleteProgram(proGramBox);

            GLES30.glDeleteBuffers(1, new int[]{vboCoordBox}, 0);
            GLES30.glDeleteBuffers(1, new int[]{vboTexBox}, 0);
            GLES30.glDeleteBuffers(1, new int[]{vboFacesBox}, 0);
            GLES30.glDeleteTextures(1, new int[]{textureId}, 0);
        }
    }

    public void render(Matrix44F projectionMatrix, Matrix44F cameraview, Vec2F size) {
        float size0 = size.data[0];
        float size1 = size.data[1];

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboCoordBox);
        float height = size0 / 1000;
        float[][] cube_vertices = {
                {size0 / 2, size1 / 2, 0},
                {size0 / 2, -size1 / 2, 0},
                {-size0 / 2, -size1 / 2, 0},
                {-size0 / 2, size1 / 2, 0}};
        FloatBuffer cubeVerticesBuffer = FloatBuffer.wrap(flatten(cube_vertices));
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, cubeVerticesBuffer.limit() * 4, cubeVerticesBuffer, GLES30.GL_DYNAMIC_DRAW);

        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glUseProgram(proGramBox);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboCoordBox);
        GLES30.glEnableVertexAttribArray(posCoordBox);
        GLES30.glVertexAttribPointer(posCoordBox, 3, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboTexBox);
        GLES30.glEnableVertexAttribArray(posTexBox);
        GLES30.glVertexAttribPointer(posTexBox, 2, GLES30.GL_UNSIGNED_BYTE, false, 0, 0);
        GLES30.glUniformMatrix4fv(posTransBox, 1, false, getGLMatrix(cameraview), 0);
        float[] matrix = getGLMatrix(projectionMatrix);
        Matrix.translateM(matrix, 0, 0, -0.005f, 0);//视频左边未对齐，进行平移处理
        GLES30.glUniformMatrix4fv(posProjBox, 1, false, matrix, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboFacesBox);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_FAN, 4, GLES30.GL_UNSIGNED_SHORT, 0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
    }

    public int texId() {
        return textureId;
    }
}
