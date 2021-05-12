package com.song.sunset.design

import com.song.sunset.design.structural.proxy.ProxyTicket
import org.junit.Test

/**
 * Desc:    在代理模式（Proxy Pattern）中，一个类代表另一个类的功能。这种类型的设计模式属于结构型模式。
 *          在代理模式中，我们创建具有现有对象的对象，以便向外界提供功能接口。
 *          注意事项：
 *          1、和适配器模式的区别：适配器模式主要改变所考虑对象的接口，而代理模式不能改变所代理类的接口。
 *          2、和装饰器模式的区别：装饰器模式为了增强功能，而代理模式是为了加以控制。
 *
 *          动态代理详见 DynamicProxyActivity.kt 类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 9:38
 */
class ProxyTest {

    @Test
    fun proxy() {
        val ticket = ProxyTicket()
        ticket.buyTicket()
    }
}