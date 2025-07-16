package com.song.sunset.base.net

import com.song.sunset.base.api.WholeApi
import com.song.sunset.base.net.OkHttpClient.obtainClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Song on 2016/12/12.
 * Email:z53520@qq.com
 */
object Net {
    fun <T> createService(clazz: Class<T>?): T {
        return createService(clazz, WholeApi.COMIC_NEW_BASE_URL)
    }

    @JvmStatic
    fun <T> createService(clazz: Class<T>?, baseUrl: String?): T {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)                                           //设置baseUrl
                .client(obtainClient())                                     //设置OKHttpClient
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //Rx转换器
                .addConverterFactory(GsonConverterFactory.create())         //gson转化器
                .addConverterFactory(StringConverterFactory.create())       //String转换器
                .build()
        return retrofit.create(clazz)
    }
}