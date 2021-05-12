package com.song.sunset.design.structural.flyweight;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 9:26
 */
public class ConcreteFlyweight implements IFlyweight {

    private final String intrinsicState;

    public ConcreteFlyweight(String intrinsicState) {
        this.intrinsicState = intrinsicState;
    }

    @Override
    public void operation(String extrinsicState) {
        System.out.println("Object address: " + this.hashCode());
        System.out.println("intrinsicState=" + intrinsicState);
        System.out.println("extrinsicState=" + extrinsicState);
    }
}
