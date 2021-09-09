package com.song.sunset.base.net;

import com.song.sunset.base.api.WholeApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Song on 2016/12/12.
 * Email:z53520@qq.com
 */

public class Net {

    public static <T> T createService(Class<T> clazz) {
        return createService(clazz, WholeApi.COMIC_NEW_BASE_URL);
    }

    public static <T> T createService(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl
                .baseUrl(baseUrl)
                //设置OKHttpClient
                .client(OkHttpClient.Companion.getInstance().createClient())
                //Rx转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                //String转换器
                .addConverterFactory(StringConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
