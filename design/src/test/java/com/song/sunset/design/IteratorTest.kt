package com.song.sunset.design

import com.song.sunset.design.behavioral.iterator.NameContainer
import org.junit.Test

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/17 11:03
 */
class IteratorTest {

    @Test
    fun iterator() {
        val nameContainer = NameContainer()
        val iterator = nameContainer.iterator
        while (iterator.hasNext()) {
            val name = iterator.next() as String
            println("Name : $name")
        }
    }
}