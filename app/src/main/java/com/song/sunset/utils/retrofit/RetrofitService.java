package com.song.sunset.utils.retrofit;

import com.song.sunset.utils.service.WholeApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by songmw3 on 2016/12/12.
 */

public class RetrofitService {

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, WholeApi.COMIC_BASE_URL);
    }

    public static <T> T createApi(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                //设置OKHttpClient
                .client(OkHttpClient.INSTANCE.getOkHttpClient())
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
