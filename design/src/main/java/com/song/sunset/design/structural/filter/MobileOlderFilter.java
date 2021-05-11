package com.song.sunset.design.structural.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 10:23
 */
public class MobileOlderFilter implements MobileFilter {
    @Override
    public List<MobileInfo> filter(List<MobileInfo> src) {
        ArrayList<MobileInfo> dest = new ArrayList<>();
        if (src != null && !src.isEmpty()) {
            for (MobileInfo info : src) {
                if (info.duration >= 3) {
                    dest.add(info);
                }
            }
        }
        return dest;
    }
}