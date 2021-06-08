package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.widget.CenteredImageSpan
import kotlinx.android.synthetic.main.activity_centered_imagespan.*

/**
 * @author songmingwen
 * @description
 * @since 2020/5/19
 */

class CenteredImageSpanActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CenteredImageSpanActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centered_imagespan)

        text_view.text = replace(this, "这里是文字这里是文字这里是文字", R.drawable.blackhole, R.drawable.end)
        text_view1.text = replace(this, "这里是文字这里是文字这里是文字", R.drawable.blackhole, R.drawable.end)
        text_view2.text = replace(this, "这里是文字这里是文字这里是文字", R.drawable.blackhole, R.drawable.end)
    }

    /**
     * 将文本中的表情符号转换为表情图片
     *
     * @param text
     * @return
     */
    fun replace(context: Context, text: CharSequence, upResId: Int, downResId: Int): CharSequence? {
        // SpannableString连续的字符串，长度不可变，同时可以附加一些object;可变的话使用SpannableStringBuilder，参考sdk文档
        val ss = SpannableString("[up_quote]$text[down_quote]$text[down_quote]")
        // 得到要显示图片的资源
        val upDrawable = context.resources.getDrawable(upResId)
        val downDrawable = context.resources.getDrawable(downResId)
        val downDrawable1 = context.resources.getDrawable(downResId)
        // 设置高度
        upDrawable.setBounds(0, 0, upDrawable.intrinsicWidth, upDrawable.intrinsicHeight)
        downDrawable.setBounds(0, 0, downDrawable.intrinsicWidth, downDrawable.intrinsicHeight)
        downDrawable1.setBounds(0, 0, downDrawable.intrinsicWidth, downDrawable.intrinsicHeight)
        // 跨度底部应与周围文本的基线对齐
        val upSpan = CenteredImageSpan(upDrawable)
        val downSpan = CenteredImageSpan(downDrawable)
        val downSpan1 = CenteredImageSpan(downDrawable1)
        // 附加图片
        ss.setSpan(upSpan, 0, "[up_quote]".length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        ss.setSpan(downSpan, "[up_quote]".length + text.length, "[up_quote]".length + text.length + "[down_quote]".length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        ss.setSpan(downSpan1, "[up_quote]".length + text.length + "[down_quote]".length + text.length, "[up_quote]".length + text.length + "[down_quote]".length + text.length + "[down_quote]".length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return ss
    }
}