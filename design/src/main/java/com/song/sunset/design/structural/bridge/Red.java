package com.song.sunset.design.structural.bridge;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:50
 */
public class Red implements Color {
    @Override
    public void fillColor(String shape) {
        System.out.println(shape + " : red");
    }
}
