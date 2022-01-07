package com.song.sunset.hook.hookdangerapi;

import android.content.Context;
import android.util.Log;

import com.song.sunset.hook.HookInterface;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.hookdangerapi.type.def.HookPackageManager;
import com.song.sunset.hook.hookdangerapi.type.def.HookTelephonyManager;
import com.song.sunset.hook.record.DefaultRecord;
import com.song.sunset.hook.record.RecordInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:    监控危险 api 调用管理类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 9:50
 */
public class HookDangerApiClient {

    private static final String TAG = "sunset-HookDangerApiClient";

    private static HookDangerApiClient INSTANCE;

    private final ArrayList<HookInterface> hook;

    private RecordInterface record;

    private boolean showResult;

    private HookDangerApiClient() {
        hook = new ArrayList<>();
    }

    public static HookDangerApiClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HookDangerApiClient();
        }
        return INSTANCE;
    }

    /*** 开始监控 */
    public void startObserve(Context context) {
        Log.i(TAG, "startObserve");
        showResult = false;
        hook.clear();
        record = new DefaultRecord();
        hook.add(new HookPackageManager(record));
        hook.add(new HookTelephonyManager(record));
        for (HookInterface hookInter : hook) {
            hookInter.startObserve(context);
        }
    }

    /*** 结束监控并保存数据 */
    public void stopObserve() {
        Log.i(TAG, "stopObserve");
        if (hook == null || hook.isEmpty()) {
            return;
        }
        for (HookInterface hookInter : hook) {
            hookInter.stopObserve();
        }
        if (record != null) {
            record.save();
        }
        hook.clear();
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