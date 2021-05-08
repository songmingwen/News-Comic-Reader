package com.song.sunset.design.factory;

import com.song.sunset.design.factory.shape.Circle;
import com.song.sunset.design.factory.shape.Shape;
import com.song.sunset.design.factory.shape.Square;
import com.song.sunset.design.factory.shape.Triangle;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 14:56
 */
public class ShapeFactory {

    public static final String CIRCLE = "CIRCLE";

    public static final String TRIANGLE = "TRIANGLE";

    public static final String SQUARE = "SQUARE";

    //使用 getShape 方法获取形状类型的对象
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase(CIRCLE)) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase(TRIANGLE)) {
            return new Triangle();
        } else if (shapeType.equalsIgnoreCase(SQUARE)) {
            return new Square();
        }
        return null;
    }
}
