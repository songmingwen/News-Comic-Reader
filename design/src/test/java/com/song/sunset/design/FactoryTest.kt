package com.song.sunset.design

import com.song.sunset.design.creational.factory.shape.Shape
import com.song.sunset.design.creational.factory.ShapeFactory
import com.song.sunset.design.creational.factory.ShapeFactory.*
import com.song.sunset.design.creational.factory.abs.ColorFactory.*
import com.song.sunset.design.creational.factory.abs.FactoryProducer
import com.song.sunset.design.creational.factory.abs.FactoryProducer.COLOR
import com.song.sunset.design.creational.factory.abs.FactoryProducer.SHAPE
import com.song.sunset.design.creational.factory.color.Color
import org.junit.Test


/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 14:59
 */
class FactoryTest {
    @Test
    fun factory() {
        val shapeFactory = ShapeFactory()

        val shape1: Shape = shapeFactory.getShape(CIRCLE)
        shape1.draw()

        val shape2: Shape = shapeFactory.getShape(TRIANGLE)
        shape2.draw()

        val shape3: Shape = shapeFactory.getShape(SQUARE)
        shape3.draw()
    }

    @Test
    fun abstractFactory() {
        val shapeFactory = FactoryProducer.getFactory(SHAPE)
        val shape1: Shape = shapeFactory.getShape(CIRCLE)
        shape1.draw()

        val shape2: Shape = shapeFactory.getShape(TRIANGLE)
        shape2.draw()

        val shape3: Shape = shapeFactory.getShape(SQUARE)
        shape3.draw()

        val colorFactory = FactoryProducer.getFactory(COLOR)
        val color1: Color = colorFactory.getColor(BLUE)
        color1.fill()

        val color2: Color = colorFactory.getColor(GREEN)
        color2.fill()

        val color3: Color = colorFactory.getColor(RED)
        color3.fill()
    }
}