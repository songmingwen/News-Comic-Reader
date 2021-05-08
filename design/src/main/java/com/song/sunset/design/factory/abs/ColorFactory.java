package com.song.sunset.design.factory.abs;

import com.song.sunset.design.factory.color.Blue;
import com.song.sunset.design.factory.color.Color;
import com.song.sunset.design.factory.color.Green;
import com.song.sunset.design.factory.color.Red;
import com.song.sunset.design.factory.shape.Shape;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 15:16
 */
public class ColorFactory extends AbstractFactory {

    public static final String BLUE = "BLUE";

    public static final String GREEN = "GREEN";

    public static final String RED = "RED";

    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equalsIgnoreCase(BLUE)) {
            return new Blue();
        } else if (color.equalsIgnoreCase(GREEN)) {
            return new Green();
        } else if (color.equalsIgnoreCase(RED)) {
            return new Red();
        }
        return null;
    }
}
