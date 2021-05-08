package com.song.sunset.design.creational.factory.shape;


/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 14:55
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("draw: square");
    }
}
