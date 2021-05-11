package com.song.sunset.design

import com.song.sunset.design.structural.composite.Bags
import com.song.sunset.design.structural.composite.Book
import com.song.sunset.design.structural.composite.Coke
import com.song.sunset.design.structural.composite.IceCream
import org.junit.Test

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 11:12
 */
class CompositeTest {

    @Test
    fun composite() {

        val book1 = Book()
        val book2 = Book()
        val book3 = Book()

        val coke1 = Coke()
        val coke2 = Coke()

        val iceCream = IceCream()

        val bigBag = Bags("大号袋子", 0.5)

        val midBag1 = Bags("中号袋子", 0.3)
        val midBag2 = Bags("中号袋子", 0.3)

        val smallBag1 = Bags("小号袋子", 0.2)
        val smallBag2 = Bags("小号袋子", 0.2)
        val smallBag3 = Bags("小号袋子", 0.2)

        smallBag1.addGoods(coke1)
        smallBag1.addGoods(coke2)

        smallBag2.addGoods(iceCream)

        midBag1.addGoods(book1)
        midBag1.addGoods(book2)
        midBag1.addGoods(book3)

        midBag2.addGoods(smallBag1)
        midBag2.addGoods(smallBag2)

        bigBag.addGoods(midBag1)
        bigBag.addGoods(midBag2)
        bigBag.addGoods(smallBag3)

        bigBag.show()
        println("一共价格=" + bigBag.price())
    }
}