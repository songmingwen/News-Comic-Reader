package com.song.sunset.design

import com.song.sunset.design.structural.filter.MobileInfo
import com.song.sunset.design.structural.filter.MobileOlderFilter
import com.song.sunset.design.structural.filter.MobileRichFilter
import com.song.sunset.design.structural.filter.MobileVipFilter
import org.junit.Test
import java.util.*

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/11 10:05
 */
class FilterTest {

    @Test
    fun filter() {
        val mobileInfo1 = MobileInfo("张一丰",1, 18, 38, 2)
        val mobileInfo2 = MobileInfo("张二丰",1, 25, 108, 2)
        val mobileInfo3 = MobileInfo("张三丰",1, 32, 68, 7)
        val mobileInfo4 = MobileInfo("赵敏一",0, 22, 38, 7)
        val mobileInfo5 = MobileInfo("赵敏二",0, 32, 168, 10)
        val mobileInfo6 = MobileInfo("赵敏三",0, 50, 18, 20)

        val src = ArrayList<MobileInfo>()
        src.add(mobileInfo1)
        src.add(mobileInfo2)
        src.add(mobileInfo3)
        src.add(mobileInfo4)
        src.add(mobileInfo5)
        src.add(mobileInfo6)

        val older = MobileOlderFilter()
        println(older.filter(src))

        val rich = MobileRichFilter()
        println(rich.filter(src))

        val vip = MobileVipFilter()
        println(vip.filter(src))

    }
}