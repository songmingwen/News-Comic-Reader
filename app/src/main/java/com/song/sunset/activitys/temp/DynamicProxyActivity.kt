package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.song.sunset.R
import com.song.sunset.interfaces.ICar
import com.song.sunset.utils.Golf
import com.song.sunset.utils.ProxyKotlinDynamicHandler
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class DynamicProxyActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, DynamicProxyActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_proxy)
    }

    fun origin(view: View) {
        val golf = Golf()
        golf.wheel()
    }

    fun dynamicProxy(view: View) {
        val proxy = ProxyKotlinDynamicHandler()
        val iCar = proxy.newInstance(Golf()) as ICar
        iCar.timeToMarket(this)
        iCar.wheel()
        iCar.engine()
        iCar.level()
    }
}
