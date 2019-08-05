package com.song.sunset.widget.opengl.air.objects

/**
 * @author songmingwen
 * @description
 * @since 2019/7/26
 */
object TeachObjectBuilder {

    fun getSourceArray(): FloatArray {
        return floatArrayOf(
                //两个三角
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f
                //线段
                - 0.5f, 0f,
                0.5f, 0f,
                //两个点
                0f, 0.25f,
                0f, -0.25f
        )
    }

}