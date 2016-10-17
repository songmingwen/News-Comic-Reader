package com.song.sunset.utils.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
public enum RetrofitBuilder {

    INSTANCE;

    private final Retrofit.Builder retrofitBuilder;

    RetrofitBuilder() {
        retrofitBuilder = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OkHttpClient.INSTANCE.getOkHttpClient())

                //Rx
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                //String转换器
                .addConverterFactory(StringConverterFactory.create())

                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
        ;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return retrofitBuilder;
    }
}
