package com.song.sunset.base.autoservice.interceptor;

import com.google.auto.service.AutoService;
import com.song.sunset.base.net.OfflineCacheControlInterceptor;

import okhttp3.Interceptor;

/**
 * Desc:    这个缓存是网络的max-age=60 表明60秒内只请求一次网络，只设置这个不设置上面的，无网情况不会显示缓存内容
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/9 16:29
 */
@AutoService(NetworkInterceptor.class)
public class NetInterceptor_OfflineCache implements NetworkInterceptor{
    @Override
    public Interceptor createInterceptor() {
        return new OfflineCacheControlInterceptor();
    }
}
