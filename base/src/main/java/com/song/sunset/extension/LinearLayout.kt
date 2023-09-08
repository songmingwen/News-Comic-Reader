package com.song.sunset

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.song.sunset.base.R
import com.song.sunset.utils.ViewUtil

/**
 * Desc:    扩展函数
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/7 9:07
 */

fun LinearLayout.addButton(title: String, onClick: (View) -> Unit): Button {
    val button = Button(context).apply {
        text = title
        textSize = 15f
        setTextColor(resources.getColor(R.color.white))
        setBackgroundResource(R.drawable.shape_blue_bg)
        setOnClickListener { onClick(this) }
    }
    val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    params.topMargin = ViewUtil.dip2px(5f)
    params.bottomMargin = ViewUtil.dip2px(5f)
    addView(button, params)
    return button
}