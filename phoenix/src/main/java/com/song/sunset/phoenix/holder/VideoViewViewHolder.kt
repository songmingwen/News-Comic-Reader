package com.song.sunset.phoenix.holder

import android.net.Uri
import android.view.View
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.R2
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.song.sunset.utils.FrescoUtil
import com.zhihu.android.sugaradapter.Layout

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 18:43
 */
@Layout(R2.layout.item_phoenix_video_view)
class VideoViewViewHolder(view: View) : PhoenixBottomViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.title)
    private val image: SimpleDraweeView = view.findViewById(R.id.phoenix_image)

    override fun onBindData(data: PhoenixChannelBean) {
        super.onBindData(data)
        title.text = data.title
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(data.thumbnail?: ""))
                .setAutoPlayAnimations(true)
                .setOldController(image.controller)
                .build()
        image.hierarchy = FrescoUtil.getHierarchy(FrescoUtil.NO_CIRCLE, false)
        image.controller = controller
    }

}