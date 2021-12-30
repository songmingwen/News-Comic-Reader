package com.song.sunset.hook.filter;

import com.song.sunset.hook.bean.RecordData;

import java.util.ArrayList;

/**
 * Desc:    记录危险 api 调用的删选器
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 11:04
 */
public interface Filter {
    /**
     * 删选器接口
     *
     * @param allData 记录已经调用的所有危险 api
     * @param current  当前调用的危险 api
     * @return true：记录，false：不记录
     */
    boolean filter(ArrayList<RecordData> allData, RecordData current);
}
