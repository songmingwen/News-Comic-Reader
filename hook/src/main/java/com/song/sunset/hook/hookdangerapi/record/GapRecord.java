package com.song.sunset.hook.hookdangerapi.record;

import android.content.Context;

import com.song.sunset.base.utils.SPUtils;
import com.song.sunset.utils.JsonUtil;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.record.DefaultRecord;

import java.util.List;

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

    /**
     * 从磁盘取数据
     */
    public static List<RecordData> getRecordFromDisk(Context context) {
        String json = SPUtils.getStringByName(context, FIELD_NAME, "");
        List<RecordData> list = JsonUtil.jsonStringToList(json, RecordData.class);
        return list;
    }
}
