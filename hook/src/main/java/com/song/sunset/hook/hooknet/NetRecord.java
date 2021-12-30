package com.song.sunset.hook.hooknet;

import com.song.sunset.base.AppConfig;
import com.song.sunset.base.utils.SPUtils;
import com.song.sunset.utils.JsonUtil;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.record.DefaultRecord;

import java.util.List;

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

    /**
     * 从磁盘取数据
     */
    public static List<RecordData> getRecordFromDisk() {
        String json = SPUtils.getStringByName(AppConfig.getApp(), FIELD_NAME, "");
        List<RecordData> list = JsonUtil.jsonStringToList(json, RecordData.class);
        return list;
    }
}
