package com.song.sunset.base.net

import com.song.sunset.base.autoservice.ServiceProvider
import com.song.sunset.base.autoservice.interceptor.Interceptor
import com.song.sunset.base.autoservice.interceptor.NetworkInterceptor
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
object OkHttpClient {

    private const val TIMEOUT_READ = 15
    private const val TIMEOUT_CONNECTION = 15

    private var okHttpClient: okhttp3.OkHttpClient

    init {
        okHttpClient = initClient()
    }

    private fun initClient(): okhttp3.OkHttpClient {
        val builder = okhttp3.OkHttpClient.Builder()

        //通过 auto service 收集拦截器并添加到网络库
        ServiceProvider.loadService(Interceptor::class.java).filterNotNull().forEach {
            builder.addInterceptor(it.createInterceptor())
        }
        //通过 auto service 收集拦截器并添加到网络库
        ServiceProvider.loadService(NetworkInterceptor::class.java).filterNotNull().forEach {
            builder.addNetworkInterceptor(it.createInterceptor())
        }

        try {
            VerifierManager.setTrustAllManager(builder)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

        return builder
                .cache(CacheUtil.getCache())//设置Cache目录
                //.cookieJar()//添加cookie
                .retryOnConnectionFailure(true)//失败重连
//                .sslSocketFactory(HttpsUtil.createDefaultSSLSocketFactory(), HttpsUtil.UnSafeTrustManager())
                .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS)
                .build()
    }

    fun obtainClient(): okhttp3.OkHttpClient {
        return okHttpClient
    }
}