package com.song.sunset.design.structural.proxy;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 10:08
 */
public class OfficialTicket implements ITicket{
    @Override
    public void buyTicket() {
        System.out.println("官方已出票");
    }
}
