package com.song.sunset.design.structural.composite;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 11:39
 */
public class Coke implements Goods {
    @Override
    public double price() {
        return 2.5;
    }

    @Override
    public void show() {
        System.out.println("可乐 2.5 元");
    }
}
