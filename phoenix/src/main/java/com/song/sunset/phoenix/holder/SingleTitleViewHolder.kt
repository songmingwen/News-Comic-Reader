package com.song.sunset.phoenix.holder

import android.view.View
import android.widget.TextView
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.R2
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.zhihu.android.sugaradapter.Layout

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 17:59
 */
@Layout(R2.layout.item_phoenix_single_title)
class SingleTitleViewHolder(view: View) : PhoenixBottomViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.title)

    override fun onBindData(data: PhoenixChannelBean) {
        super.onBindData(data)
        title.text = data.title
    }

}