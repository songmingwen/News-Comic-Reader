package com.song.sunset.design.structural.decorator;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 15:47
 */
public class GoldDecorator extends ColorDecorator{

    private Shape shape;

    public GoldDecorator(Shape shape) {
        this.shape = shape;
    }

    @Override
    public String getDesc() {
        return "金色" + shape.desc;
    }

    @Override
    public void draw() {
        shape.draw();
        System.out.println("涂上金色");
    }
}
