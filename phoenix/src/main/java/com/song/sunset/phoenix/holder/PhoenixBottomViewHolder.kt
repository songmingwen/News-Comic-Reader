package com.song.sunset.phoenix.holder

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.view.SimpleDraweeView
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.zhihu.android.sugaradapter.SugarHolder

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 18:48
 */
open class PhoenixBottomViewHolder(view: View) : SugarHolder<PhoenixChannelBean>(view) {

    companion object {
        const val PHOENIX_NEWS_URL = "phoenix_news_url"
    }

    private val txSource: TextView = view.findViewById(R.id.tx_source)
    private val commentCount: TextView = view.findViewById(R.id.comment_count)
    private val picCount: TextView = view.findViewById(R.id.pic_count)
    private val updateTime: TextView = view.findViewById(R.id.update_time)
    private val imgSource: SimpleDraweeView = view.findViewById(R.id.img_source)

    override fun onBindData(data: PhoenixChannelBean) {
        if (data.subscribe != null) {
            txSource.text = data.subscribe.catename
            if (!TextUtils.isEmpty(data.subscribe.logo)) {
                imgSource.visibility = View.VISIBLE
                imgSource.setImageURI(data.subscribe.logo)
            }
            if (data.style.slideCount != 0) {
                picCount.visibility = View.VISIBLE
                picCount.text = "${data.style.slideCount}"
            }
        }

        if (!TextUtils.equals(data.commentsall, "0")) {
            commentCount.visibility = View.VISIBLE
            commentCount.text = data.commentsall
        }

        if (!TextUtils.isEmpty(data.updateTime) && data.updateTime.length > 12) {
            updateTime.text = data.updateTime.substring(11)
        }

        itemView.setOnClickListener(View.OnClickListener {
            ARouter.getInstance().build("/song/phoenix/news")
                    .withString(PHOENIX_NEWS_URL, data.link.weburl)
                    .navigation()
        })
    }

}