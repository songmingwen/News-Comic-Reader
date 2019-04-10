package com.song.sunset.utils

import java.util.*

/**
 * @author Song
 * @description 测试算法：自然数数组元素重新排序，使数组的偶数在奇数的前面
 */
class Exam{

    @org.junit.Test
    fun test(){
        val a = intArrayOf(1, 2, 3, 4, 5, 6, 7)
        trans(a)
        print(Arrays.toString(a))//结果{2,4,6,1,3,5,7},不要求顺序{6,2,4,7,5,3,1}也可以。
    }

    private fun trans(array: IntArray) {
        // TODO 此处答题

    }

}