package com.song.sunset.design

import com.song.sunset.design.builder.ComputerClient
import org.junit.Test

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 16:47
 */
class BuilderTest {
    @Test
    fun builder() {
        var computerClient = ComputerClient.Builder()
                .keyboard("机械键盘")
                .build()
        computerClient.printComputer()
        println("--------------")

        computerClient = computerClient.newBuilder().display("华为智慧屏").build()
        computerClient.printComputer()
        println("--------------")

        val computerClient1 = ComputerClient()
        computerClient1.printComputer()
    }
}