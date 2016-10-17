package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.service.Retrofit2Service;
/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public enum RetrofitApi {

    INSTANCE;

    private Retrofit2Service retrofit2Service;

    RetrofitApi() {
        retrofit2Service = RetrofitBuilder.INSTANCE.getRetrofitBuilder()
                .baseUrl(Retrofit2Service.COMIC_BASE_URL)
                .build()
                .create(Retrofit2Service.class);
    }

    public Retrofit2Service getRetrofit2Service() {
        return retrofit2Service;
    }
}
