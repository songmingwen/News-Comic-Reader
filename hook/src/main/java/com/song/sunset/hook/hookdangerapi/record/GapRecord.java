package com.song.sunset.hook.hookdangerapi.record;

import com.song.sunset.hook.record.DefaultRecord;

/**
 * Desc:    此类用于记录一段时间内多次调用某个 api 的需求
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 15:08
 */
public class GapRecord extends DefaultRecord {

    public static final String FIELD_NAME = "mgtv_hook_danger_api_gap";

    @Override
    protected String getKey() {
        return FIELD_NAME;
    }

}
