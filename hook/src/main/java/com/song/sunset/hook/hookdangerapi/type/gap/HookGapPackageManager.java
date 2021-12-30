package com.song.sunset.hook.hookdangerapi.type.gap;

import com.song.sunset.hook.filter.Filter;
import com.song.sunset.hook.hookdangerapi.filter.GapFilter;
import com.song.sunset.hook.hookdangerapi.type.def.HookPackageManager;
import com.song.sunset.hook.record.RecordInterface;

/**
 * Desc:    用于记录一段时间内多次调用 PackageManagerApi 的需求
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 10:24
 */
public class HookGapPackageManager extends HookPackageManager {

    public HookGapPackageManager(RecordInterface record) {
        super(record);
    }

    @Override
    protected Filter getFilter() {
        return new GapFilter();
    }
}
