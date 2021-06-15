package com.song.sunset.phoenix.holder

import android.text.TextUtils
import android.util.Log
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.zhihu.android.sugaradapter.SugarAdapter
import com.zhihu.android.sugaradapter.SugarHolder
import java.util.*

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 17:17
 */
object PhoenixHolderDispatcher {

    private const val TITLE_IMAGE = "titleimg" //左图右文
    private const val SLIDE_IMAGE = "slideimg" //三小图
    private const val BIG_IMG = "bigimg" //大图
    private const val NINE_LATTICE = "ninelattice" // 九宫格 style.images
    private const val COMMIT_BIG_IMG = "commitbigimg" //commitbigimg-thumbnail 字段
    private const val COMMIT_BIG_VIDEO_IMG = "commitvideobigimg" //commitbigimg-thumbnail 字段

    val s = HashSet<String>()

    fun addHolders(builder: SugarAdapter.Builder): SugarAdapter.Builder {
        return builder.add(TitleImageViewHolder::class.java)
                .add(SlideImageViewHolder::class.java)
                .add(BigImageViewHolder::class.java)
                .add(VideoViewViewHolder::class.java)
                .add(NineLatticeViewHolder::class.java)
    }

    fun dispatch(phoenixChannelBean: PhoenixChannelBean): Class<out SugarHolder<*>> {
        if (phoenixChannelBean.style == null || TextUtils.isEmpty(phoenixChannelBean.style.view)) {
            return TitleImageViewHolder::class.java
        }
        val viewType: String = phoenixChannelBean.style.view
        Log.i("phoenix type=", viewType + " ,title=" + phoenixChannelBean.title)
        s.add(viewType)
        return when (viewType) {
            TITLE_IMAGE -> {
                TitleImageViewHolder::class.java
            }
            SLIDE_IMAGE -> {
                SlideImageViewHolder::class.java
            }
            BIG_IMG, COMMIT_BIG_IMG -> {
                BigImageViewHolder::class.java
            }
            COMMIT_BIG_VIDEO_IMG -> {
                VideoViewViewHolder::class.java
            }
            NINE_LATTICE -> {
                if (phoenixChannelBean.style != null && phoenixChannelBean.style.images != null
                        && phoenixChannelBean.style.images.isNotEmpty()) {
                    NineLatticeViewHolder::class.java
                } else {
                    TitleImageViewHolder::class.java
                }
            }
            else -> {
                TitleImageViewHolder::class.java
            }
        }
    }
}