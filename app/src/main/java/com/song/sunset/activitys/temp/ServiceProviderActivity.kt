package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.song.sunset.R
import com.song.sunset.base.autoservice.ServiceProvider
import com.song.sunset.base.autoservice.demo.Interface
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.beans.User
import kotlinx.android.synthetic.main.activity_service_provider.*
import java.lang.StringBuilder

/**
 * AutoService 使用指南
 * 可以很方便用于组件化开发
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

        val string = StringBuilder()
        // 此处模拟通过网络请求获取到 app 配置数据，通过 AutoService 把数据传递给注册此接口的实例。
        val user = User("宋先生", "地球-中国", "10010")
        // 获取指定接口的所有实现类，收集或传递数据
        ServiceProvider.loadService(Interface::class.java).filterNotNull().forEach {
            string.append(it.string).append(",")
            it.doSomething(user.toString())
        }
        txt_Impl.text = string
    }
}
