package com.song.sunset.design.creational.factory.shape;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 14:51
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("draw: circle");
    }
}
