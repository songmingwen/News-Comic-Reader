package com.song.sunset.base.autoservice.interceptor;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/9 16:11
 */
public interface NetworkInterceptor {
    okhttp3.Interceptor createInterceptor();
}
