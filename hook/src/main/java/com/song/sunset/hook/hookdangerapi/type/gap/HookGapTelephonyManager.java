package com.song.sunset.hook.hookdangerapi.type.gap;

import com.song.sunset.hook.filter.Filter;
import com.song.sunset.hook.hookdangerapi.filter.GapFilter;
import com.song.sunset.hook.hookdangerapi.type.def.HookTelephonyManager;
import com.song.sunset.hook.record.RecordInterface;

/**
 * Desc:    用于记录一段时间内多次调用 TelephoneManagerApi 的需求
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 10:55
 */
public class HookGapTelephonyManager extends HookTelephonyManager {

    public HookGapTelephonyManager(RecordInterface record) {
        super(record);
    }

    @Override
    protected Filter getFilter() {
        return new GapFilter();
    }
}
