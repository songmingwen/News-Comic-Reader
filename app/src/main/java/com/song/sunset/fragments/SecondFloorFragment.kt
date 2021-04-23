package com.song.sunset.fragments

import com.scwang.smart.refresh.header.TwoLevelHeader
import com.scwang.smart.refresh.layout.api.RefreshHeader

/**
 * Copyright(C),2006-2021,快乐阳光互动娱乐传媒有限公司
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/23 11:52
 */
class SecondFloorFragment : ComicBaseListFragment() {
    override fun getRefreshHeader(): RefreshHeader {
        val secondFloor = TwoLevelHeader(context)
        secondFloor.setFloorRate(1.3f)
        secondFloor.setEnablePullToCloseTwoLevel(true)
        secondFloor.setBottomPullUpToCloseRate(1f)
        return secondFloor
    }
}