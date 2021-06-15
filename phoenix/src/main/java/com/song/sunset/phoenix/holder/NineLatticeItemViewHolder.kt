package com.song.sunset.phoenix.holder

import android.net.Uri
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.song.sunset.base.AppConfig
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.R2
import com.song.sunset.phoenix.holder.PhoenixBottomViewHolder.Companion.PHOENIX_NEWS_URL
import com.song.sunset.utils.ScreenUtils
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/11 16:37
 */
@Layout(R2.layout.item_phoenix_nine_lattice_item)
class NineLatticeItemViewHolder(view: View) : SugarHolder<String>(view) {

    private var image: SimpleDraweeView = view.findViewById(R.id.nineItem)

    override fun onBindData(data: String) {
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(data))
                .setAutoPlayAnimations(true)
                .setOldController(image.controller)
                .build()

        val corner = ScreenUtils.dp2Px(context, 3f)
        val roundingParams = RoundingParams().apply {
            cornersRadii = floatArrayOf(corner, corner, corner, corner, corner, corner, corner, corner)
        }
        val builder = GenericDraweeHierarchyBuilder(AppConfig.getApp().resources)
        val hierarchy = builder.setPlaceholderImage(android.R.color.white)
                .setPlaceholderImage(R.drawable.icon_placeholder)
                .setRetryImage(R.drawable.icon_new_style_retry)
                .setRetryImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setFailureImage(R.drawable.icon_new_style_failure)
                .setRoundingParams(roundingParams)
                .setFadeDuration(50)
                .build()
        image.hierarchy = hierarchy
        image.controller = controller

    }

    fun setWidth(width: Float, url: String) {
        val layoutParams = image.layoutParams
        layoutParams.width = width.toInt()
        layoutParams.height = width.toInt()
        image.layoutParams = layoutParams

        itemView.setOnClickListener(View.OnClickListener {
            ARouter.getInstance().build("/song/phoenix/news")
                    .withString(PHOENIX_NEWS_URL, url)
                    .navigation()
        })
    }

}