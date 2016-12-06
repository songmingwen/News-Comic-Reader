package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.service.WholeApi;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public enum Retrofit {

    INSTANCE;

    private retrofit2.Retrofit retrofit;

    Retrofit() {
        retrofit = RetrofitBuilder.INSTANCE.getRetrofitBuilder()
                .baseUrl(WholeApi.COMIC_BASE_URL)
                .build();
    }

    public <T> T getRetrofitApi(Class<T> cls) {
        return retrofit.create(cls);
    }
}
