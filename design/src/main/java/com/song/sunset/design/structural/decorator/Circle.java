package com.song.sunset.design.structural.decorator;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 15:40
 */
public class Circle extends Shape {

    public Circle() {
        desc = "圆";
    }

    @Override
    public void draw() {
        System.out.println("画一个" + getDesc());
    }
}
