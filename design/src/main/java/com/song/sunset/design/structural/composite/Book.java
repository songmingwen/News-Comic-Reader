package com.song.sunset.design.structural.composite;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 11:43
 */
public class Book implements Goods {
    @Override
    public double price() {
        return 20;
    }

    @Override
    public void show() {
        System.out.println("书 20 元");
    }
}
