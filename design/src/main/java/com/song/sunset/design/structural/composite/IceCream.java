package com.song.sunset.design.structural.composite;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 11:41
 */
public class IceCream implements Goods {
    @Override
    public double price() {
        return 3.0;
    }

    @Override
    public void show() {
        System.out.println("冰淇淋 3 元");
    }
}
