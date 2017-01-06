package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.service.WholeApi;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by songmw3 on 2016/12/12.
 */

public class RetrofitService {

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, WholeApi.COMIC_BASE_URL, true);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        return createApi(clazz, baseUrl, true);
    }

    /**
     * @param clazz           api接口类
     * @param baseUrl         域名地址
     * @param addPublicParams true表示添加公共参数
     * @param <T>             api类
     * @return
     */
    public static <T> T createApi(Class<T> clazz, String baseUrl, boolean addPublicParams) {
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl
                .baseUrl(baseUrl)
                //设置OKHttpClient
                .client(OkHttpClient.INSTANCE.getOkHttpClient(addPublicParams))
                //Rx
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //String转换器
                .addConverterFactory(StringConverterFactory.create())
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public static <T> T createApi(Class<T> clazz, Interceptor publicParams) {
        return createApi(clazz, WholeApi.COMIC_BASE_URL, publicParams);
    }

    /**
     * @param clazz        api接口类
     * @param baseUrl      域名地址
     * @param publicParams 需要添加的公共参数拦截器
     *                     实例如下：
     *                     new BasicParamsInterceptor.Builder()
     *                     .addHeaderParam("device_id", "Song_phone")
     *                     .addParam("uid", "Song_uid")
     *                     .addQueryParam("api_version", "1.0001")
     *                     .build();
     * @param <T>          api类
     * @return
     */
    public static <T> T createApi(Class<T> clazz, String baseUrl, Interceptor publicParams) {
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl
                .baseUrl(baseUrl)
                //设置OKHttpClient
                .client(OkHttpClient.INSTANCE.getOkHttpClient(publicParams))
                //Rx
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //String转换器
                .addConverterFactory(StringConverterFactory.create())
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
