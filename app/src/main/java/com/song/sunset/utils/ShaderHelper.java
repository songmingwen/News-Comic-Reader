package com.song.sunset.utils;

import static android.opengl.GLES20.*;

import android.util.Log;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/15
 */
public class ShaderHelper {

    public static final String TAG = ShaderHelper.class.toString();

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shader = glCreateShader(type);
        if (shader == 0) {
            Log.w(TAG, "could not create new shader");
            return 0;
        }
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);

        int status = getShaderiv(shader);

        getShaderLogInfo(shaderCode, shader);

        if (status == 0) {
            Log.w(TAG, "could not create new shader");
            return 0;
        }
        return shader;
    }

    /**
     * 取出着色器日志信息
     *
     * @param shaderCode
     * @param shader
     */
    private static void getShaderLogInfo(String shaderCode, int shader) {
        Log.i(TAG, "着色器\n" + shaderCode + ":" + glGetShaderInfoLog(shader));
    }

    /**
     * 取出编译状态
     *
     * @param shader
     */
    private static int getShaderiv(int shader) {
        final int[] status = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, status, 0);
        return status[0];
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        int program = glCreateProgram();
        if (program == 0) {
            Log.w(TAG, "could not create new program");
            return program;
        }
        glAttachShader(program, vertexShaderId);
        glAttachShader(program, fragmentShaderId);
        glLinkProgram(program);
        return program;
    }

}
