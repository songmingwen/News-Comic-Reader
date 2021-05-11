package com.song.sunset.design.structural.composite;

import java.util.ArrayList;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 11:45
 */
public class Bags implements Goods {

    public ArrayList<Goods> list = new ArrayList<>();

    public String name;

    public double bagPrice;

    public Bags(String name, double bagPrice) {
        this.name = name;
        this.bagPrice = bagPrice;
    }

    public void addGoods(Goods goods) {
        list.add(goods);
    }

    @Override
    public double price() {
        double total = bagPrice;
        if (list.isEmpty()) {
            return total;
        }
        for (Goods goods : list) {
            total += goods.price();
        }
        return total;
    }

    @Override
    public void show() {
        if (list.isEmpty()) {
            System.out.println("空袋子=" + name + ",价格=" + bagPrice);
            return;
        }
        System.out.println("袋子=" + name + ",价格=" + bagPrice);
        for (Goods goods : list) {
            goods.show();
        }
    }
}
