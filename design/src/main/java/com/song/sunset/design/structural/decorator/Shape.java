package com.song.sunset.design.structural.decorator;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 15:37
 */
public abstract class Shape {

    String desc = "没有描述";

    public String getDesc() {
        return desc;
    }

    public abstract void draw();
}
