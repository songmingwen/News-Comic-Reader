package com.song.sunset.base.autoservice.interceptor;

import com.google.auto.service.AutoService;

/**
 * Desc:    打印日志
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/9 16:23
 */
@AutoService(Interceptor.class)
public class Interceptor_HttpLogging implements Interceptor {
    @Override
    public okhttp3.Interceptor createInterceptor() {
        okhttp3.logging.HttpLoggingInterceptor logInterceptor = new okhttp3.logging.HttpLoggingInterceptor();
        logInterceptor.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }
}
