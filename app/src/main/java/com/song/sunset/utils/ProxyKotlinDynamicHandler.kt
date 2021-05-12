package com.song.sunset.utils

import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * @author songmingwen
 * @description
 * @since 2019/3/5
 */
class ProxyKotlinDynamicHandler : InvocationHandler {

    private var mTarget: Any? = null

    fun newInstance(target: Any): Any {
        this.mTarget = target
        return Proxy.newProxyInstance(target.javaClass.classLoader, target.javaClass.interfaces, this)
    }

    //Java 代码正常运行，Kotlin 代码运行 crash。改为 *args.orEmpty 解决问题
    @Throws(Throwable::class)
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        Log.e(this.javaClass.toString(), "before")
        val result = method?.invoke(mTarget, *args.orEmpty())
        Log.e(this.javaClass.toString(), "after")
        return result ?: Unit
    }
}