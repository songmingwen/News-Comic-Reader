package com.song.sunset.design

import com.song.sunset.design.structural.facade.ShapeFacade
import org.junit.Test

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 15:59
 */
class FacadeTest {

    @Test
    fun facade() {
        val shapeFacade = ShapeFacade()
        shapeFacade.drawCircle()
        shapeFacade.drawTriangle()
        shapeFacade.drawSquare()
    }
}