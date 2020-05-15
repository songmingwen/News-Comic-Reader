package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.song.core.autoservice.InterfaceA
import com.song.core.autoservice.InterfaceB
import com.song.core.autoservice.ServiceProvider
import com.song.sunset.R
import com.song.sunset.activitys.base.BaseActivity
import kotlinx.android.synthetic.main.activity_service_provider.*
import java.lang.StringBuilder

/**
 * AutoService 使用指南
 */
class ServiceProviderActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ServiceProviderActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider)
        ServiceProvider.optional(InterfaceA::class.java).ifPresent {
            txt_ImplA.text = it.getString("extra")
        }
        val string: StringBuilder = StringBuilder()
        ServiceProvider.loadService(InterfaceB::class.java).filterNotNull().forEach {
            string.append(it.string).append(",")
        }
        txt_ImplB.text = string
    }
}
