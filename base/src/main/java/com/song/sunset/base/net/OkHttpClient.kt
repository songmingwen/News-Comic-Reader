package com.song.sunset.base.net

import com.song.sunset.base.autoservice.ServiceProvider
import com.song.sunset.base.autoservice.interceptor.NetworkInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
class OkHttpClient private constructor() {
    private var okHttpClient: okhttp3.OkHttpClient? = null
    private fun initClient() {
        val builder = okhttp3.OkHttpClient.Builder()

        ServiceProvider.loadService(com.song.sunset.base.autoservice.interceptor.Interceptor::class.java).filterNotNull().forEach {
            builder.addInterceptor(it.createInterceptor())
        }

        ServiceProvider.loadService(NetworkInterceptor::class.java).filterNotNull().forEach {
            builder.addInterceptor(it.createInterceptor())
        }

        okHttpClient = builder
                //设置Cache目录
                .cache(CacheUtil.getCache())
                //添加cookie
                //.cookieJar()
                //失败重连
                .retryOnConnectionFailure(true) //https
                .sslSocketFactory(HttpsUtil.createDefaultSSLSocketFactory()) //time out
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