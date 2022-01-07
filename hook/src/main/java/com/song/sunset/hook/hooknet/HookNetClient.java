package com.song.sunset.hook.hooknet;

import android.content.Context;
import android.util.Log;

import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.record.RecordInterface;

import java.util.List;

/**
 * Desc:    监控网络请求
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/27 10:33
 */
public class HookNetClient {

    private static final String TAG = "sunset-HookNetClient";

    private static HookNetClient INSTANCE;

    private RecordInterface record;

    private HookNet hookNet;

    private boolean showResult;

    private HookNetClient() {
    }

    public static HookNetClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HookNetClient();
        }
        return INSTANCE;
    }

    public void startObserve(Context context) {
        Log.i(TAG, "startObserve");
        showResult = false;
        record = new NetRecord();
        hookNet = new HookNet(record);
        hookNet.startObserve(context);
    }

    public void stopObserve() {
        Log.i(TAG, "stopObserve");
        if (hookNet == null) {
            return;
        }
        hookNet.stopObserve();
        if (record != null) {
            record.save();
        }
        hookNet = null;
        showResult = true;
    }

    /**
     * 获取内存数据
     */
    public List<RecordData> getRecord() {
        if (record != null) {
            return record.getRecord();
        } else {
            return null;
        }
    }

    public boolean showResult() {
        return showResult && getRecord() != null && !getRecord().isEmpty();
    }
}
