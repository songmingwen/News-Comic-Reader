package com.song.sunset.design.structural.proxy;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 10:10
 */
public class ProxyTicket implements ITicket {

    public ProxyTicket() {
        System.out.println("代理购票处");
    }

    @Override
    public void buyTicket() {
        OfficialTicket officialTicket = new OfficialTicket();
        officialTicket.buyTicket();
    }
}
