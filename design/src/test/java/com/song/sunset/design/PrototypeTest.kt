package com.song.sunset.design

import com.song.sunset.design.creational.prototype.ShapeCache
import org.junit.Test

/**
 * Desc:    原型模式（Prototype Pattern）是用于创建重复的对象，同时又能保证性能。这种类型的设计模式
 *          属于创建型模式，它提供了一种创建对象的最佳方式。这种模式是实现了一个原型接口，该接口用于
 *          创建当前对象的克隆。当直接创建对象的代价比较大时，则采用这种模式。例如，一个对象需要在一个
 *          高代价的数据库操作之后被创建。我们可以缓存该对象，在下一个请求时返回它的克隆，在需要的时候
 *          更新数据库，以此来减少数据库调用。
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 9:35
 */
class PrototypeTest {

    @Test
    fun prototype() {
        ShapeCache.loadShape()
        val clone = ShapeCache.getCloneShape("1")
        val origin = ShapeCache.getShape("1")
        println("clone = " + clone.type)
        println("origin = " + origin.type)
        println("----------------------")
        clone.type = "Circle change"
        println("clone = " + clone.type)
        println("origin = " + origin.type)
        println("----------------------")
    }
}