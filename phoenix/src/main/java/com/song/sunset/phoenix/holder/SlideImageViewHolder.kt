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
import java.util.*

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 18:42
 */
@Layout(R2.layout.item_phoenix_slide_image)
class SlideImageViewHolder(view: View) : PhoenixBottomViewHolder(view)  {

    companion object{
        fun getSimpleDraweeViews(image1: SimpleDraweeView, image2: SimpleDraweeView, image3: SimpleDraweeView): ArrayList<SimpleDraweeView> {
            val imageList = ArrayList<SimpleDraweeView>()
            imageList.add(image1)
            imageList.add(image2)
            imageList.add(image3)
            return imageList
        }

        fun setSlideImage(phoenixChannelBean: PhoenixChannelBean, imageList: ArrayList<SimpleDraweeView>) {
            for (i in 0..2) {
                if (phoenixChannelBean.style.images != null) {
                    val count = phoenixChannelBean.style.images.size
                    if (i <= count) {
                        val simpleDraweeView = imageList[i]
                        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                                .setUri(Uri.parse(phoenixChannelBean.style.images[i % count]?: ""))
                                .setAutoPlayAnimations(true)
                                .setOldController(simpleDraweeView.controller)
                                .build()
                        simpleDraweeView.hierarchy = FrescoUtil.getHierarchy(FrescoUtil.NO_CIRCLE, false)
                        simpleDraweeView.controller = controller
                    } else {
                        val simpleDraweeView = imageList[i]
                        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                                .setUri(Uri.parse(phoenixChannelBean.thumbnail?: ""))
                                .setAutoPlayAnimations(true)
                                .setOldController(simpleDraweeView.controller)
                                .build()
                        simpleDraweeView.hierarchy = FrescoUtil.getHierarchy(FrescoUtil.NO_CIRCLE, false)
                        simpleDraweeView.controller = controller
                    }
                }
            }
        }
    }

    private val title: TextView = view.findViewById(R.id.title)
    private val image1: SimpleDraweeView = view.findViewById(R.id.phoenix_image_1)
    private val image2: SimpleDraweeView = view.findViewById(R.id.phoenix_image_2)
    private val image3: SimpleDraweeView = view.findViewById(R.id.phoenix_image_3)

    override fun onBindData(data: PhoenixChannelBean) {
        super.onBindData(data)
        title.text = data.title
        setSlideImage(data, getSimpleDraweeViews(image1, image2, image3))
    }
}