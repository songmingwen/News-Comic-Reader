package com.song.sunset.hook.filter;


import com.song.sunset.hook.bean.RecordData;

import java.util.ArrayList;

/**
 * Desc:    默认筛选器
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 11:05
 */
public class DefaultFilter implements Filter {

    @Override
    public boolean filter(ArrayList<RecordData> allData, RecordData current) {
        return current != null;
    }
}
