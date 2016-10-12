package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.service.Retrofit2Service;

/**
 * Created by Song on 2016/9/20 0020.
 * Email:z53520@qq.com
 */
public class RetrofitUtil {

    public static Retrofit2Service getRetrofit2Service() {
        return RetrofitApi.INSTANCE.getRetrofit2Service();
    }
}
