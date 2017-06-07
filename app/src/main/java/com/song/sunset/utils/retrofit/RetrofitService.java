package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.api.WholeApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Song on 2016/12/12.
 * Email:z53520@qq.com
 */

public class RetrofitService {

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, WholeApi.COMIC_BASE_URL);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl
                .baseUrl(baseUrl)
                //设置OKHttpClient
                .client(OkHttpClient.INSTANCE.getOkHttpClient())
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
