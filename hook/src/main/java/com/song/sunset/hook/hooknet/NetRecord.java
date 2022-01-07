package com.song.sunset.hook.hooknet;

import com.song.sunset.hook.record.DefaultRecord;

/**
 * Desc:    记录监控网络请求
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/27 10:39
 */
public class NetRecord extends DefaultRecord {

    public static final String FIELD_NAME = "mgtv_hook_net_gap";

    @Override
    protected String getKey() {
        return FIELD_NAME;
    }

}
