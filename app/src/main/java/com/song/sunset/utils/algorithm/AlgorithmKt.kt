package com.song.sunset.utils.algorithm

import kotlin.random.Random

/**
 * @author songmingwen
 * @description 算法工具类
 * @since 2019/12/11
 */

class AlgorithmKt {
    companion object {

        /**
         * @size    数组长度
         * @rangeLeft   元素随机左边界
         * @rangeRight  元素随机右边界
         */
        fun generateRandomArray(size: Int, rangeLeft: Int, rangeRight: Int): IntArray {
            val start = Math.min(rangeLeft, rangeRight)
            val end = Math.max(rangeLeft, rangeRight)
            val length = end - start
            return IntArray(size) { Random.nextInt(length) + start }
        }

        /**
         * 校验是否排序成功
         */
        fun isSorted(array: IntArray): Boolean {
            for (i in 0 until array.size - 1) {
                if (array[i] > array[i + 1]) {
                    return false
                }
            }
            return true
        }

        fun swap(array: IntArray, a: Int, b: Int) {
            val temp = array[a]
            array[a] = array[b]
            array[b] = temp
        }

    }

}