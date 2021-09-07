package com.song.sunset.holders

import android.view.View
import android.widget.TextView
import com.song.sunset.R
import com.song.sunset.R2
import com.song.sunset.beans.User
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarHolder

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/7 11:47
 */
@Layout(R2.layout.scrollactivity_recyclerview_item_header)
class HeaderHolder (view: View) : SugarHolder<User>(view) {
    private val name = view.findViewById<TextView>(R.id.name)
    private val address = view.findViewById<TextView>(R.id.address)
    private val phone = view.findViewById<TextView>(R.id.phone)
    override fun onBindData(data: User) {
        name.text = data.userName
        address.text = data.address
        phone.text = data.phone
    }

}