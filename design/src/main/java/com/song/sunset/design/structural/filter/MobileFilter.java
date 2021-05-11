package com.song.sunset.design.structural.filter;

import java.util.List;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 10:20
 */
public interface MobileFilter {

    /**
     * 手机账号筛选器接口
     * @param src 需要被处理的数据
     * @return  经过处理后的数据
     */
    List<MobileInfo> filter(List<MobileInfo> src);

}
