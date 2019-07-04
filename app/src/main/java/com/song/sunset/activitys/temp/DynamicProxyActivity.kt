package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.song.sunset.R
import com.song.sunset.interfaces.IOrigin
import com.song.sunset.utils.JdkDynamicProxy
import com.song.sunset.utils.OriginImpl

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
        val origin = OriginImpl()
        origin.originMethod()
    }

    fun dynamicProxy(view: View) {
        val proxy = JdkDynamicProxy()
//        val proxy = com.song.sunset.utils.DynamicProxy()
        val iOrigin = proxy.bind(OriginImpl()) as IOrigin
        iOrigin.originMethod()
    }
}
