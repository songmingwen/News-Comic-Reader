package com.song.sunset.base.autoservice.interceptor;

/**
 * Desc:    网络拦截器
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/9 16:02
 */
public interface Interceptor {
    okhttp3.Interceptor createInterceptor();
}
