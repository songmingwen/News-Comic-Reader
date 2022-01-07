package com.song.sunset.hook.hookdangerapi;

import android.content.Context;
import android.util.Log;

import com.song.sunset.hook.HookInterface;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.hookdangerapi.record.GapRecord;
import com.song.sunset.hook.hookdangerapi.type.gap.HookGapPackageManager;
import com.song.sunset.hook.hookdangerapi.type.gap.HookGapTelephonyManager;
import com.song.sunset.hook.record.RecordInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:    监控危险 api 调用管理类
 * 此类用于记录一段时间内多次调用某个 api 的需求
 * <p>
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 10:19
 */
public class HookGapDangerApiClient {

    private static final String TAG = "sunset-HookGapDangerApiClient";

    private static HookGapDangerApiClient INSTANCE;

    private final ArrayList<HookInterface> hook;

    private RecordInterface record;

    public HookGapDangerApiClient() {
        hook = new ArrayList<>();
    }

    public static HookGapDangerApiClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HookGapDangerApiClient();
        }
        return INSTANCE;
    }

    /**
     * 开始监控
     */
    public void startObserve(Context context) {
        Log.i(TAG, "startObserve");
        hook.clear();

        record = new GapRecord();
        hook.add(new HookGapPackageManager(record));
        hook.add(new HookGapTelephonyManager(record));

        for (HookInterface hookInter : hook) {
            hookInter.startObserve(context);
        }
    }

    /**
     * 结束监控
     */
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
        return getRecord() != null && !getRecord().isEmpty();
    }
}
