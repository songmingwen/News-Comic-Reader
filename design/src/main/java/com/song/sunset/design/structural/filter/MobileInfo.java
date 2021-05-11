package com.song.sunset.design.structural.filter;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 10:16
 */
public class MobileInfo {

    public MobileInfo(String name, int gender, int age, int price, int duration) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.price = price;
        this.duration = duration;
    }

    public String name;

    /**
     * 用户性别:1男，0女
     */
    public int gender;

    /**
     * 用户年龄
     */
    public int age;

    /**
     * 套餐价格
     */
    public int price;

    /**
     * 网龄
     */
    public int duration;

    @Override
    public String toString() {
        return "MobileInfo{" +
                "name='" + name + '\'' +
                '}';
    }
}
