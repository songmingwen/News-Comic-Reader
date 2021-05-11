package com.song.sunset.design.structural.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 10:41
 */
public class MobileRichFilter implements MobileFilter {

    @Override
    public List<MobileInfo> filter(List<MobileInfo> src) {
        ArrayList<MobileInfo> dest = new ArrayList<>();
        if (src != null && !src.isEmpty()) {
            for (MobileInfo info : src) {
                if (info.price >= 100) {
                    dest.add(info);
                }
            }
        }
        return dest;
    }
}
