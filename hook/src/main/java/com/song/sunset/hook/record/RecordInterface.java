package com.song.sunset.hook.record;

import com.song.sunset.hook.bean.RecordData;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:    记录管理类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 14:47
 */
public interface RecordInterface {

    /**
     * 开始记录到内存
     */
    void record(ArrayList<RecordData> data);

    /**
     * 获取记录数据
     */
    List<RecordData> getRecord();

    /**
     * 持久化记录
     */
    void save();

    void clear();
}
