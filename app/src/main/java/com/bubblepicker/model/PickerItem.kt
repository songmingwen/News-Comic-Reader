package com.bubblepicker.model

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.song.sunset.utils.ViewUtil

data class PickerItem @JvmOverloads constructor(
        var title: String? = null,                  //气泡中显示的文字
        var icon: Drawable? = null,                 //文字旁的图标
        var iconOnTop: Boolean = true,              //图标在文字顶部或者底部
        @ColorInt var color: Int? = null,           //气泡颜色（蒙层层）
        var gradient: BubbleGradient? = null,       //气泡颜色，会覆盖 color 的值（蒙层）
        var overlayAlpha: Float = 1f,             //选中状态蒙层透明度，1f 代表全透明
        var typeface: Typeface = Typeface.DEFAULT,  //文字字体
        @ColorInt var textColor: Int? = null,       //文字颜色
        var textSize: Float = ViewUtil.dip2px(18f).toFloat(),                  //文字大小
        var backgroundImage: Drawable? = null,      //气泡背景 drawable（选中状态可见）
        var isSelected: Boolean = false,            //选中状态
        var customData: Any? = null)