package com.song.sunset.holders

import android.view.View
import android.widget.TextView
import com.song.sunset.R
import com.song.sunset.R2
import com.song.sunset.beans.FuncItem
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/10 16:55
 */
@Layout(R2.layout.item_function)
class FunctionItemHolder(view: View) : SugarHolder<FuncItem>(view) {

    private val name = view.findViewById<TextView>(R.id.function_name)

    override fun onBindData(data: FuncItem) {
        name.text = data.name
        name.setOnClickListener {
            data.onClick.invoke(name)
        }
    }
}