package com.song.sunset.design.creational.prototype;

import java.util.HashMap;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 9:30
 */
public class ShapeCache {

    private final static HashMap<String, Shape> shapeHashMap = new HashMap();

    public static Shape getCloneShape(String id) {
        Shape shape = shapeHashMap.get(id);
        if (shape == null) {
            return null;
        }
        return (Shape) shape.clone();
    }

    public static Shape getShape(String id) {
        return shapeHashMap.get(id);
    }

    public static void loadShape() {
        //模拟从数据库读取数据
        Circle circle = new Circle();
        circle.setId("1");
        shapeHashMap.put(circle.getId(), circle);

        Square square = new Square();
        square.setId("2");
        shapeHashMap.put(square.getId(), square);

        Rectangle rectangle = new Rectangle();
        rectangle.setId("3");
        shapeHashMap.put(rectangle.getId(), rectangle);
    }
}
