package com.song.sunset.design.creational.single

/**
 * Desc:    单例模式
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 11:03
 */
class SingleTonKt private constructor() {
    companion object {
        val instance: SingleTonKt by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SingleTonKt()
        }
    }
}