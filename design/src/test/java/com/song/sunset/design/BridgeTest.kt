package com.song.sunset.design

import com.song.sunset.design.structural.bridge.*
import org.junit.Test

/**
 * Desc:    桥接（Bridge）是用于把抽象化与实现化解耦，使得二者可以独立变化。这种类型的设计模式属于
 *          结构型模式，它通过提供抽象化和实现化之间的桥接结构，来实现二者的解耦。
 *
 *          模式使用场景
 *          1、如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间
 *          建立静态的继承联系，通过桥接模式可以使它们在抽象层建立一个关联关系。
 *          2、对于那些不希望使用继承或因为多层次继承导致系统类的个数急剧增加的系统，桥接模式尤为适用。
 *          3、一个类存在两个独立变化的维度，且这两个维度都需要进行扩展。
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:29
 */
class BridgeTest {

    @Test
    fun bridge() {
        val rectangle = Rectangle()
        val circle = Circle()
        val square = Square()

        val green = Green()
        val red = Red()
        val blue = Blue()

        rectangle.setColor(green)
        rectangle.draw()
        rectangle.setColor(red)
        rectangle.draw()
        rectangle.setColor(blue)
        rectangle.draw()

        circle.setColor(green)
        circle.draw()
        circle.setColor(red)
        circle.draw()
        circle.setColor(blue)
        circle.draw()

        square.setColor(green)
        square.draw()
        square.setColor(red)
        square.draw()
        square.setColor(blue)
        square.draw()
    }
}