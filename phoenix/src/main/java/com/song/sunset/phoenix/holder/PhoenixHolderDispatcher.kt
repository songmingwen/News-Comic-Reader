package com.song.sunset.phoenix.holder

import android.text.TextUtils
import android.util.Log
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.zhihu.android.sugaradapter.SugarAdapter
import com.zhihu.android.sugaradapter.SugarHolder

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 17:17
 */
object PhoenixHolderDispatcher {

    private const val SINGLE_TITLE = "singletitle" //单标题
    private const val TITLE_IMAGE = "titleimg" //左图右文
    private const val SLIDE_IMAGE = "slideimg" //三小图
    private const val SLIDE_IMAGE2 = "slideimg2" //一大两小
    private const val BIG_IMG = "bigimg" //大图
    private const val BIG_IMG2 = "bigimg2" //美女段子大图
    private const val VIDEO = "video" //视频当前页播放
    private const val MATCH_SCORE = "matchscore" //比赛有比分
    private const val MATCH_SCOMPRE = "matchscompre" //比赛无比分
    private const val MATCH_IMG = "matchimg" //比赛底图
    private const val LONG_IMG = "longimg" //直播长图
    private const val LIVE_IMG = "liveimg" //直播浮层
    private const val BIG_TOPIC = "bigtopic" //大专题
    private const val LIST_FOCUS_SLIDER = "focusslider"
    private const val TOPIC_TITLE = "topictitle"
    private const val TOPIC_BANNER_ADV = "topicbanneradv"
    private const val VIDEO_BIG_IMG = "videobigimg" //视频大图当页播放样式

    fun addHolders(builder: SugarAdapter.Builder): SugarAdapter.Builder {
        return builder.add(SingleTitleViewHolder::class.java)
                .add(TitleImageViewHolder::class.java)
                .add(SlideImageViewHolder::class.java)
                .add(SlideImage2ViewHolder::class.java)
                .add(BigImageViewHolder::class.java)
                .add(VideoViewViewHolder::class.java)
    }

    fun dispatch(phoenixChannelBean: PhoenixChannelBean): Class<out SugarHolder<*>> {
        if (phoenixChannelBean.style == null || TextUtils.isEmpty(phoenixChannelBean.style.view)) {
            return SingleTitleViewHolder::class.java
        }
        val viewType: String = phoenixChannelBean.style.view
        Log.i("phoenix type=", viewType)
        return when (viewType) {
            SINGLE_TITLE -> {
                SingleTitleViewHolder::class.java
            }
            TITLE_IMAGE -> {
                TitleImageViewHolder::class.java
            }
            SLIDE_IMAGE -> {
                SlideImageViewHolder::class.java
            }
            SLIDE_IMAGE2 -> {
                SlideImage2ViewHolder::class.java
            }
            BIG_IMG -> {
                BigImageViewHolder::class.java
            }
            BIG_IMG2 -> {
                SingleTitleViewHolder::class.java
            }
            VIDEO -> {
                VideoViewViewHolder::class.java
            }
            MATCH_SCORE -> {
                SingleTitleViewHolder::class.java
            }
            MATCH_SCOMPRE -> {
                SingleTitleViewHolder::class.java
            }
            MATCH_IMG -> {
                SingleTitleViewHolder::class.java
            }
            LONG_IMG -> {
                SingleTitleViewHolder::class.java
            }
            LIVE_IMG -> {
                SingleTitleViewHolder::class.java
            }
            BIG_TOPIC -> {
                SingleTitleViewHolder::class.java
            }
            LIST_FOCUS_SLIDER -> {
                SingleTitleViewHolder::class.java
            }
            TOPIC_TITLE -> {
                SingleTitleViewHolder::class.java
            }
            TOPIC_BANNER_ADV -> {
                SingleTitleViewHolder::class.java
            }
            VIDEO_BIG_IMG -> {
                SingleTitleViewHolder::class.java
            }
            else -> {
                SingleTitleViewHolder::class.java
            }
        }
    }
}