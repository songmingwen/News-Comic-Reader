package com.song.sunset.phoenix.holder

import android.view.View
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.R2
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.zhihu.android.sugaradapter.Layout

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 18:43
 */
@Layout(R2.layout.item_phoenix_slide_image2)
class SlideImage2ViewHolder(view: View) : PhoenixBottomViewHolder(view)  {

    private val title: TextView = view.findViewById(R.id.title)
    private val image1: SimpleDraweeView = itemView.findViewById(R.id.phoenix_image_1)
    private val image2: SimpleDraweeView = itemView.findViewById(R.id.phoenix_image_2)
    private val image3: SimpleDraweeView = itemView.findViewById(R.id.phoenix_image_3)

    override fun onBindData(data: PhoenixChannelBean) {
        super.onBindData(data)
        title.text = data.title
        SlideImageViewHolder.setSlideImage(data, SlideImageViewHolder.getSimpleDraweeViews(image1, image2, image3))
    }

}