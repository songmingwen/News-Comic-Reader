package com.song.sunset.hook.bean;

import java.io.Serializable;

/**
 * Desc:    危险 Api 信息类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/14 14:55
 */
public class RecordData implements Serializable {

    /*** 危险或敏感 api 名称 */
    public String apiName;

    /*** 堆栈跟踪日志或其他详细信息 */
    public String stacktrace;

    /*** 时间戳 */
    public long timestamp;

    /**
     * 需要记录日志的间隔阈值：多次请求某个 api，间隔时间小于此值时就会记录
     * 单位：毫秒
     */
    public long recordGapTime;

    /**
     * 用于记录多次请求某个 api 的间隔时间。
     */
    public String gapTime;

    public RecordData(String apiName, String stacktrace) {
        this.apiName = apiName;
        this.stacktrace = stacktrace;
        this.timestamp = System.currentTimeMillis();
    }

    public RecordData(String apiName, String stacktrace, long recordGapTime) {
        this.apiName = apiName;
        this.stacktrace = stacktrace;
        this.recordGapTime = recordGapTime;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "DangerApiInfo{" +
                "apiName='" + apiName + '\n' +
                ", timestamp=" + timestamp +
                ", recordGapTime=" + recordGapTime +
                ", gapTime='" + gapTime + '\n' +
                ", stacktrace='" + stacktrace + '\'' +
                '}';
    }
}
