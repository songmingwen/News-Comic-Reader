package com.song.sunset.utils.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
public enum OkHttpClient {

    INSTANCE;

    private final okhttp3.OkHttpClient.Builder okHttpClientBuilder;

    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;

    OkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor cacheInterceptor = new OfflineCacheControlInterceptor();

        okHttpClientBuilder = new okhttp3.OkHttpClient.Builder()
                //打印日志
                .addInterceptor(logInterceptor)

                //设置缓存
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)

                //设置Cache目录
                .cache(CacheUtil.getCache())

                //添加cookie
//                .cookieJar()

                //失败重连
                .retryOnConnectionFailure(true)

                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
    }

    public okhttp3.OkHttpClient getOkHttpClient(boolean addPublicParams) {
        if (addPublicParams) {
            //添加默认公共请求参数
            return okHttpClientBuilder.addInterceptor(getPublicParams()).build();
        } else {
            return okHttpClientBuilder.build();
        }
    }

    public okhttp3.OkHttpClient getOkHttpClient(Interceptor publicParams) {
        return okHttpClientBuilder.addInterceptor(publicParams).build();
    }

    public Interceptor getPublicParams() {
        return new BasicParamsInterceptor.Builder()
                .addHeaderParam("device_id", "S_phone")
                .addParam("uid", "S_uid")
                .addQueryParam("api_version", "1.0001")
                .build();
    }
}
