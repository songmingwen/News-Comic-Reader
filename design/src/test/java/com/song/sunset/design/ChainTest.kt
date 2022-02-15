package com.song.sunset.design

import com.song.sunset.design.behavioral.chain.LogChan
import com.song.sunset.design.behavioral.chain.Logger
import org.junit.Test

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 15:22
 */
class ChainTest {

    @Test
    fun chain() {
        val logger = LogChan.getLogChain()
        logger.log(Logger.ERROR,"这里发生 crash")
        println("---------------------")
        logger.log(Logger.DEBUG,"debug 显示的信息")
        println("---------------------")
        logger.log(Logger.INFO,"一些辅助信息")
    }
}