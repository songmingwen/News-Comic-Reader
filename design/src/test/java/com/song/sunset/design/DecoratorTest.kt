package com.song.sunset.design

import com.song.sunset.design.structural.decorator.Circle
import com.song.sunset.design.structural.decorator.GoldDecorator
import org.junit.Test

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 15:52
 */
class DecoratorTest {

    @Test
    fun decorator(){
        val circle = Circle()
        val goldDecorator = GoldDecorator(circle)
        circle.draw()
        println("---------------------")
        goldDecorator.draw()
    }
}