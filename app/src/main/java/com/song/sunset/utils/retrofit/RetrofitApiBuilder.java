package com.song.sunset.utils.retrofit;


/**
 * Created by Song on 2016/9/20 0020.
 * Email:z53520@qq.com
 */
public class RetrofitApiBuilder {

    public static <T> T getRetrofitApi(Class<T> cls) {
        return Retrofit.INSTANCE.getRetrofitApi(cls);
    }
}
