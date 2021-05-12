package com.song.sunset.design.structural.flyweight;

import java.util.HashMap;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 9:28
 */
public class FlyweightFactory {

    private static HashMap<String, IFlyweight> map = new HashMap<>();

    public static IFlyweight getFlyweight(String intrinsicState) {
        if (!map.containsKey(intrinsicState)) {
            map.put(intrinsicState, new ConcreteFlyweight(intrinsicState));
        }
        return map.get(intrinsicState);
    }
}
