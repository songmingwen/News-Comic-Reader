package com.song.sunset.utils.retrofit;

import android.text.TextUtils;

import com.song.sunset.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 * 离线读取本地缓存，在线获取最新数据(读取单个请求的请求头，亦可统一设置)
 */
public class OfflineCacheControlInterceptor implements Interceptor {

    private static final int MAX_STALE = 60 * 60 * 24 * 28;//4个星期  这个缓存是本地的，无网络的时候会强制使用缓存。
    private static final int MAX_AGE = 0;//max-age=60 表明60秒内只请求一次网络，只设置这个不设置上面的，无网情况不会显示缓存内容

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetWorkUtils.isNetWorking()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)     //强制使用缓存
                    .build();
        }

        Response response = chain.proceed(request);

        if (NetWorkUtils.isNetWorking()) {
            //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=" + MAX_AGE;
            }
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")//移除干扰信息
                    .build();
        } else {
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_STALE)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}

