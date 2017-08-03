package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.DeviceUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
public enum OkHttpClient {

    INSTANCE;

    private final okhttp3.OkHttpClient okHttpClientBuilder;

    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;

    OkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor cacheInterceptor = new OfflineCacheControlInterceptor();

        okHttpClientBuilder = new okhttp3.OkHttpClient.Builder()
                //打印日志
                .addInterceptor(logInterceptor)
                //添加固定公共请求参数
                .addInterceptor(getPublicParams())

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
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }

    public okhttp3.OkHttpClient getOkHttpClient() {
        return okHttpClientBuilder;
    }

    //http://api.iclient.ifeng.com/ClientNews?
    // id=SYLB10,SYDT10&
    // action=down&
    // pullNum=3&
    // lastDoc=,,,&
    // province=北京市&
    // city=北京市&
    // district=朝阳区&
    // gv=5.5.4&
    // av=5.5.4&
    // uid=865982024584370&
    // deviceid=865982024584370&
    // proid=ifengnews&
    // os=android_23&
    // df=androidphone&
    // vt=5&
    // screen=1080x1920&
    // publishid=9023&
    // nw=wifi&
    // loginid=76078652

    public Interceptor getPublicParams() {
        return new BasicParamsInterceptor.Builder()
//                .addHeaderParam("device_id", "S_phone")
//                .addParam("uid", DeviceUtils.getAuthenticationID(AppConfig.getApp()))
                .addQueryParam("device_id", "S_phone")
                .addQueryParam("uid", DeviceUtils.getAuthenticationID(AppConfig.getApp()))
                .addQueryParam("id", "SYLB10,SYDT10")
                .addQueryParam("gv", "5.6.4")
                .addQueryParam("av", "5.6.4")
                .addQueryParam("nw", "wifi")
                .addQueryParam("df", "androidphone")
                .addQueryParam("publishid", "9023")
                .addQueryParam("v", "3330110")
                .build();
    }
}
