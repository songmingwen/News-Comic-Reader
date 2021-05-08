package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.DeviceUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
public class OkHttpClient {

    private static final int TIMEOUT_READ = 15;

    private static final int TIMEOUT_CONNECTION = 15;

    private static OkHttpClient sInstance = new OkHttpClient();

    public static synchronized OkHttpClient getInstance() {
        return sInstance;
    }

    private okhttp3.OkHttpClient okHttpClient;

    private OkHttpClient() {
        initClient();
    }

    private void initClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor cacheInterceptor = new OfflineCacheControlInterceptor();
        okHttpClient = new okhttp3.OkHttpClient.Builder()
                //打印日志
                .addInterceptor(logInterceptor)
                //添加固定公共请求参数
                .addInterceptor(getBasePublicParams(null))

                //这个缓存是本地的，无网络的时候会强制使用缓存
                .addInterceptor(cacheInterceptor)
                //这个缓存是网络的max-age=60 表明60秒内只请求一次网络，只设置这个不设置上面的，无网情况不会显示缓存内容
                .addNetworkInterceptor(cacheInterceptor)

                //设置Cache目录
                .cache(CacheUtil.getCache())

                //添加cookie
//                .cookieJar()

                //失败重连
                .retryOnConnectionFailure(true)

                //https
                .sslSocketFactory(HttpsUtil.createDefaultSSLSocketFactory())

                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }

    public okhttp3.OkHttpClient createClient(Map<String, String> map) {
        if (map == null) {
            if (okHttpClient == null) {
                initClient();
            }
            return okHttpClient;
        }
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor cacheInterceptor = new OfflineCacheControlInterceptor();

        return new okhttp3.OkHttpClient.Builder()
                //打印日志
                .addInterceptor(logInterceptor)
                //添加固定公共请求参数
                .addInterceptor(getBasePublicParams(map))

                //这个缓存是本地的，无网络的时候会强制使用缓存
                .addInterceptor(cacheInterceptor)
                //这个缓存是网络的max-age=60 表明60秒内只请求一次网络，只设置这个不设置上面的，无网情况不会显示缓存内容
                .addNetworkInterceptor(cacheInterceptor)

                //设置Cache目录
                .cache(CacheUtil.getCache())

                //添加cookie
//                .cookieJar()

                //失败重连
                .retryOnConnectionFailure(true)

                //https
                .sslSocketFactory(HttpsUtil.createDefaultSSLSocketFactory())

                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
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

    private Interceptor getBasePublicParams(Map<String, String> map) {

        BasicParamsInterceptor.Builder builder = new BasicParamsInterceptor.Builder();

        builder
                .addQueryParam("device_id", "S_phone")
                .addQueryParam("uid", DeviceUtils.getAuthenticationID(AppConfig.getApp()))
                .addQueryParam("id", "SYLB10,SYDT10")
                .addQueryParam("gv", "5.6.9")
                .addQueryParam("av", "5.6.9")
                .addQueryParam("nw", "wifi")
                .addQueryParam("df", "androidphone")
                .addQueryParam("publishid", "9023")
                .addQueryParam("v", "3330110")
                .addQueryParam("key", "aa32472066c50b390e4b9758f67361e8dd5498fa2460e1f97a6f7c831633e927b0290f330921b01d6885b66f0076483e41441f457c368226fd8273686a00971290137fa115de8d493c00c40b620c8c7962b716b5ef6d3b305338dc12b24ff8e7%253A%253A%253Au17");
//                .addHeaderParam("device_id", "S_phone")
//                .addParam("uid", DeviceUtils.getAuthenticationID(AppConfig.getApp()))

        if (map != null) {
            builder.addQueryParamsMap(map);
        }

        return builder.build();
    }
}
