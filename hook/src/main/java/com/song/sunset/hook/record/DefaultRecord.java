package com.song.sunset.hook.record;

import com.song.sunset.base.AppConfig;
import com.song.sunset.base.utils.SPUtils;
import com.song.sunset.utils.JsonUtil;
import com.song.sunset.hook.bean.RecordData;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:    默认记录类,只要出现就会记录
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 14:52
 */
public class DefaultRecord implements RecordInterface {

    public static final String FIELD_NAME = "mgtv_hook_danger_api_default";

    private final List<RecordData> list;

    public DefaultRecord() {
        list = new ArrayList<>();
    }

    @Override
    public void record(ArrayList<RecordData> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        list.clear();
        list.addAll(data);
    }

    @Override
    public List<RecordData> getRecord() {
        return list;
    }

    @Override
    public void save() {
        if (list == null || list.isEmpty()) {
            return;
        }
        String json = JsonUtil.genericListToJsonString(list, RecordData.class);
        writeRecord(getKey(), json);
    }

    @Override
    public void clear() {
        if (list != null) {
            list.clear();
        }
    }

    protected String getKey() {
        return FIELD_NAME;
    }

    public static void writeRecord(String key, String json) {
        SPUtils.setStringByName(AppConfig.getApp(), key, json);
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
