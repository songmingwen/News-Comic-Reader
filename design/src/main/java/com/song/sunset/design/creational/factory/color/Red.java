package com.song.sunset.design.creational.factory.color;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 15:10
 */
public class Red implements Color{
    @Override
    public void fill() {
        System.out.println("fill: red");
    }
}
