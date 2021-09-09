package com.song.sunset.base.net

import com.song.sunset.base.autoservice.ServiceProvider
import com.song.sunset.base.autoservice.interceptor.NetworkInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
class OkHttpClient private constructor() {
    private var okHttpClient: okhttp3.OkHttpClient? = null
    private fun initClient() {
        val builder = okhttp3.OkHttpClient.Builder()

        //通过 auto service 收集拦截器并添加到网络库
        ServiceProvider.loadService(com.song.sunset.base.autoservice.interceptor.Interceptor::class.java).filterNotNull().forEach {
            builder.addInterceptor(it.createInterceptor())
        }
        //通过 auto service 收集拦截器并添加到网络库
        ServiceProvider.loadService(NetworkInterceptor::class.java).filterNotNull().forEach {
            builder.addNetworkInterceptor(it.createInterceptor())
        }

        okHttpClient = builder
                .cache(CacheUtil.getCache())//设置Cache目录
                //.cookieJar()//添加cookie
                .retryOnConnectionFailure(true)//失败重连
                .sslSocketFactory(HttpsUtil.createDefaultSSLSocketFactory())
                .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS)
                .build()
    }

    fun createClient(): okhttp3.OkHttpClient? {
        if (okHttpClient == null) {
            initClient()
        }
        return okHttpClient
    }

    companion object {
        private const val TIMEOUT_READ = 15
        private const val TIMEOUT_CONNECTION = 15

        @get:Synchronized
        val instance = OkHttpClient()
    }

    init {
        initClient()
    }
}