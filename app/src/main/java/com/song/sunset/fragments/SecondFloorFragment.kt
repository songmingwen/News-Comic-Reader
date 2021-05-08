package com.song.sunset.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.scwang.smart.refresh.header.TwoLevelHeader
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import com.song.sunset.R
import kotlinx.android.synthetic.main.layout_refresh_second_floor.*

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/23 11:52
 */
class SecondFloorFragment : ComicBaseListFragment() {

    override fun getRefreshLayoutId(): Int {
        return R.layout.layout_refresh_second_floor
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)

        mSwipeRefreshLayout.setOnMultiListener(object : SimpleMultiListener() {

            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                toolbar.alpha = 1 - percent.coerceAtMost(1f)
                second_floor.alpha = (percent / 3).coerceAtMost(1f)
                second_floor.translationY = (offset - second_floor.height + toolbar.height)
                        .coerceAtMost(mSwipeRefreshLayout.layout.height - second_floor.height).toFloat()
            }

            override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
                if (oldState === RefreshState.TwoLevel) {
                    second_floor_content.animate().alpha(0f).duration = 500
                }
            }
        })

        header.setOnTwoLevelListener {
            second_floor_content.animate().alpha(1f).duration = 500
            true
        }
    }
}