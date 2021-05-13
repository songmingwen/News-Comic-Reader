package com.song.sunset.utils

import org.junit.Test
import java.util.ArrayList

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 11:39
 */
class ArrayGrowTest {

    @Test
    fun origin() {
        val list = ArrayList<Int>()
        val start = System.currentTimeMillis()
        for (index in 0..10000) {
            list.add(index)
        }
        val end = System.currentTimeMillis()
        println("time=" + (end - start))
    }

    @Test
    fun big() {
        println("big---------------------")
        val list = ArrayList<Int>(10000)
        val start = System.currentTimeMillis()
        for (index in 0..10000) {
            list.add(index)
        }
        val end = System.currentTimeMillis()
        println("time=" + (end - start))
    }
}