package com.song.sunset.base.autoservice.interceptor;

import com.google.auto.service.AutoService;
import com.song.sunset.base.net.OfflineCacheControlInterceptor;

/**
 * Desc:    这个缓存是本地的，无网络的时候会强制使用缓存
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/9 16:26
 */
@AutoService(Interceptor.class)
public class Interceptor_OfflineCache implements Interceptor{
    @Override
    public okhttp3.Interceptor createInterceptor() {
        return new OfflineCacheControlInterceptor();
    }
}
