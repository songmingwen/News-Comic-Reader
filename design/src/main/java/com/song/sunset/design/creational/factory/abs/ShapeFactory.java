package com.song.sunset.design.creational.factory.abs;

import com.song.sunset.design.creational.factory.color.Color;
import com.song.sunset.design.creational.factory.shape.Circle;
import com.song.sunset.design.creational.factory.shape.Shape;
import com.song.sunset.design.creational.factory.shape.Square;
import com.song.sunset.design.creational.factory.shape.Triangle;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 15:15
 */
public class ShapeFactory extends AbstractFactory{

    public static final String CIRCLE = "CIRCLE";

    public static final String TRIANGLE = "TRIANGLE";

    public static final String SQUARE = "SQUARE";

    @Override
    public Shape getShape(String shape) {
        if (shape == null) {
            return null;
        }
        if (shape.equalsIgnoreCase(CIRCLE)) {
            return new Circle();
        } else if (shape.equalsIgnoreCase(TRIANGLE)) {
            return new Triangle();
        } else if (shape.equalsIgnoreCase(SQUARE)) {
            return new Square();
        }
        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }
}
