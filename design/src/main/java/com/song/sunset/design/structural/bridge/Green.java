package com.song.sunset.design.structural.bridge;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:51
 */
public class Green implements Color {
    @Override
    public void fillColor(String shape) {
        System.out.println(shape + " : green");
    }
}
