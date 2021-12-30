package com.song.sunset.hook.hookdangerapi.filter;

import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.filter.Filter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Desc:    用于记录一段时间内多次调用某个 api 的筛选器
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 11:09
 */
public class GapFilter implements Filter {

    @Override
    public boolean filter(ArrayList<RecordData> allData, RecordData current) {
        if (current == null) {
            return false;
        }
        //如果没有记录，那么不用记录
        if (allData == null || allData.isEmpty()) {
            return false;
        }
        HashMap<String, RecordData> dangerMap = new HashMap<>();

        for (RecordData info : allData) {
            dangerMap.put(info.apiName, info);
        }

        //如果没有调用过这个 api ，那么不用记录
        if (dangerMap == null || !dangerMap.containsKey(current.apiName)) {
            return false;
        }

        RecordData previous = dangerMap.get(current.apiName);
        return (current.timestamp - previous.timestamp) < current.recordGapTime;
    }
}
