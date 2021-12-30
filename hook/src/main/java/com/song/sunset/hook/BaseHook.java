package com.song.sunset.hook;

import android.content.Context;
import android.util.Log;

import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.filter.DefaultFilter;
import com.song.sunset.hook.filter.Filter;
import com.song.sunset.hook.record.RecordInterface;

import java.util.ArrayList;

/**
 * Desc:    监控基类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 9:30
 */
public abstract class BaseHook implements HookInterface {

    /*** 需要记录的列表 */
    protected ArrayList<RecordData> recordList;

    /*** 记录所有的危险调用列表 */
    protected ArrayList<RecordData> totalList;

    private boolean isActive;

    private RecordInterface record;

    public BaseHook(RecordInterface record) {
        this.record = record;
    }

    @Override
    public void startObserve(Context context) {
        isActive = true;
        recordList = new ArrayList<>();
        totalList = new ArrayList<>();
    }

    @Override
    public void stopObserve() {
        isActive = false;
        record();
        recordList = null;
        totalList = null;
    }

    public void record() {
        if (recordList != null && !recordList.isEmpty() && record != null) {
            record.record(recordList);
        }
    }

    /**
     * 当发生危险 api 调用时，调用此类记录到内存
     */
    protected void append(RecordData danger) {
        Log.i("song-hook", danger.toString());
        if (!isActive) {
            return;
        }

        if (filter(danger)) {
            if (recordList != null) {
                recordList.add(danger);
            }
        }

        if (totalList != null) {
            totalList.add(danger);
        }
    }

    /**
     * 纪录日志策略：一般情况只要调用了敏感 api 就会记录
     * 可以根据需要实现此类，以实现不同的记录策略
     *
     * @return true:记录本次日志，false：不记录本次日志
     */
    private boolean filter(RecordData danger) {
        Filter dangerFilter = getFilter();
        if (dangerFilter == null) {
            return true;
        } else {
            return dangerFilter.filter(totalList, danger);
        }
    }

    /*** 记录删选器，控制记录策略 */
    protected Filter getFilter() {
        return new DefaultFilter();
    }


    protected String getThreadStacktrace() {
        return getThreadStacktrace(Thread.currentThread());
    }

    private String getThreadStacktrace(Thread thread) {
        if (thread == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            StackTraceElement[] stacktrace = thread.getStackTrace();
            sb.append("\n\nThread StackTrace:\n")
                    .append(", tid: ").append(thread.getId()).append(", name: ").append(thread.getName())
                    .append("\n")
                    .append("java stacktrace:\n");
            for (StackTraceElement element : stacktrace) {
                sb.append("    at ").append(element.toString()).append("\n");
            }
            sb.append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
