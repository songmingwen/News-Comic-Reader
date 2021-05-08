package com.song.sunset.design.factory.abs;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 15:21
 */
public class FactoryProducer {

    public static final String SHAPE = "SHAPE";

    public static final String COLOR = "COLOR";

    public static AbstractFactory getFactory(String factoryName) {
        if (factoryName == null) {
            return null;
        }
        if (factoryName.equals(SHAPE)) {
            return new ShapeFactory();
        } else if (factoryName.equals(COLOR)) {
            return new ColorFactory();
        }
        return null;
    }

}
