package com.song.sunset.design.structural.filter;

import java.util.List;

/**
 * Desc:    VIP 用户筛选器，条件：1、套餐消费大于 100，网龄大于 3 年。
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 10:42
 */
public class MobileVipFilter implements MobileFilter {

    @Override
    public List<MobileInfo> filter(List<MobileInfo> src) {
        return getRichList(getOlderList(src));
    }

    private List<MobileInfo> getOlderList(List<MobileInfo> src) {
        if (src != null && !src.isEmpty()) {
            MobileOlderFilter olderFilter = new MobileOlderFilter();
            return olderFilter.filter(src);
        }
        return null;
    }

    private List<MobileInfo> getRichList(List<MobileInfo> src) {
        if (src != null && !src.isEmpty()) {
            MobileRichFilter richFilter = new MobileRichFilter();
            return richFilter.filter(src);
        }
        return null;
    }
}
