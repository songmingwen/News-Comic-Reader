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
        val retrofit = Retrofit.Builder() //设置baseUrl
                .baseUrl(baseUrl) //设置OKHttpClient
                .client(obtainClient()) //Rx转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //gson转化器
                .addConverterFactory(GsonConverterFactory.create()) //String转换器
                .addConverterFactory(StringConverterFactory.create())
                .build()
        return retrofit.create(clazz)
    }
}