package com.song.game.wuxia.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.song.game.wuxia.bean.talent.Talent
import com.song.game.wuxia.bean.talent.TalentLevel
import com.song.sunset.R
import com.song.sunset.R2
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/10 16:55
 */
@Layout(R2.layout.item_wuxia_talent)
class TalentHolder(view: View) : SugarHolder<Talent>(view) {

    private val name = view.findViewById<TextView>(R.id.talent_name)
    private val select = view.findViewById<ImageView>(R.id.select)

    override fun onBindData(data: Talent) {
        name.text = data.name
        val back = when (data.level) {
            TalentLevel.WHITE -> R.color.wuxia_color_white
            TalentLevel.GREEN -> R.color.wuxia_color_green
            TalentLevel.BLUE -> R.color.wuxia_color_blue
            TalentLevel.PURPLE -> R.color.wuxia_color_purple
            TalentLevel.RED -> R.color.wuxia_color_red
            TalentLevel.GOLD -> R.color.wuxia_color_gold
            else -> R.color.wuxia_color_white
        }
        name.setBackgroundResource(back)

        renderSelectedState(data)

        itemView.setOnClickListener {
            data.selected = !data.selected
            renderSelectedState(data)
        }
    }

    private fun renderSelectedState(data: Talent) {
        select.visibility = if (data.selected) View.VISIBLE else View.GONE
    }
}