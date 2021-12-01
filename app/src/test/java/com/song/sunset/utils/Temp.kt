package com.song.sunset.utils

import com.song.sunset.beans.User
import org.junit.Test

/**
 * Desc:    装箱问题
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/11/5 15:00
 */
class Temp {
    /**
     * 在 JVM 平台数字存储为原生类型 int、 double 等。 例外情况是当创建可空数字引用如 Int? 或者使用泛型时。
     * 在这些场景中，数字会装箱为 Java 类 Integer、 Double 等。
     * 请注意，对相同数字的可为空引用可能是不同的对象：(boxedB、anotherBoxedB)
     *
     * 由于 JVM 对 -128 到 127 的整数（Integer）应用了内存优化，
     * 因此 a (boxedA、anotherBoxedA)的所有可空引用实际上都是同一对象。
     * 但是没有对 b (boxedB、anotherBoxedB)应用内存优化，所以它们是不同对象。
     */
    @Test
    fun testReference() {
        val a: Int = 100
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a

        val b: Int = 10000
        val boxedB: Int? = b
        val anotherBoxedB: Int? = b

        println(boxedA == anotherBoxedA) // true
        println(boxedB == anotherBoxedB) // true

        println(boxedA === anotherBoxedA) // true
        println(boxedB === anotherBoxedB) // false

    }

    @Test
    fun testTemp() {
        val user = User()
        val u = user.userName ?: return
        print(u)
    }
}