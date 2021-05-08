package com.song.sunset.design.structural.bridge;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:46
 */
public abstract class Shape {
    protected Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void draw();
}
