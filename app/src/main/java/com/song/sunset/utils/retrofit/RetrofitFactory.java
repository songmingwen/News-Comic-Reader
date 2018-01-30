package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.api.WholeApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Song on 2016/12/12.
 * Email:z53520@qq.com
 */

public class RetrofitFactory {

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, WholeApi.COMIC_NEW_BASE_URL, null);
    }

    public static <T> T createApi(Class<T> clazz, Map<String, String> map) {
        return createApi(clazz, WholeApi.COMIC_NEW_BASE_URL, map);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        return createApi(clazz, baseUrl, null);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl, String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return createApi(clazz, baseUrl, map);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl, Map<String, String> map) {
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl
                .baseUrl(baseUrl)
                //设置OKHttpClient
                .client(OkHttpClient.getInstance().createClient(map))
                //Rx转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                //String转换器
                .addConverterFactory(StringConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
