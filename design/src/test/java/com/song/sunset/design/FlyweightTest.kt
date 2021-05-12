package com.song.sunset.design

import com.song.sunset.design.structural.flyweight.FlyweightFactory
import org.junit.Test

/**
 * Desc:    享元模式（Flyweight Pattern）主要用于减少创建对象的数量，以减少内存占用和提高性能。
 *          这种类型的设计模式属于结构型模式，它提供了减少对象数量从而改善应用所需的对象结构的方式。
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 9:23
 */
class FlyweightTest {

    @Test
    fun flyweight() {
        FlyweightFactory.getFlyweight("10086").operation("充值话费")
        println("---------------------")
        FlyweightFactory.getFlyweight("10010").operation("办理宽带")
        println("---------------------")
        FlyweightFactory.getFlyweight("10086").operation("办理宽带")
    }
}