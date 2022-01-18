package com.song.sunset.phoenix.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.R2
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.song.sunset.utils.ScreenUtils
import com.song.sunset.widget.SpacesItemDecoration
import com.zhihu.android.sugaradapter.Layout
import com.zhihu.android.sugaradapter.SugarAdapter
import java.util.ArrayList

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/11 16:28
 */
@Layout(R2.layout.item_phoenix_nine_lattice)
class NineLatticeViewHolder(view: View) : PhoenixBottomViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.title)
    private val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

    override fun onBindData(data: PhoenixChannelBean) {
        super.onBindData(data)
        title.text = data.title

        if (data.style.images == null || data.style.images.isEmpty()) {
            return
        }

        val temp = ArrayList<String>()
        if (data.style.images.size > 9) {
            for (index in 0..9) {
                temp.add(data.style.images[index])
            }
        } else {
            temp.addAll(data.style.images)
        }

        val gridSize: Int = when (temp.size) {
            1 -> {
                1
            }
            2, 4 -> {
                2
            }
            else -> {
                3
            }
        }

        val recyclerViewHeight: Float = when (temp.size) {
            2 -> {
                (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2Px(context, 21f)) / 2
            }
            3 -> {
                (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2Px(context, 28f)) / 3
            }
            5, 6 -> {
                ((ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2Px(context, 28f)) / 3) * 2 + ScreenUtils.dp2Px(context, 7f)
            }
            else -> {//1,4,7,8,9
                ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2Px(context, 14f)
            }

        }

        val itemWidth: Float = when (temp.size) {
            2, 4 -> {
                (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2Px(context, 21f + 6)) / 2
            }
            3, 5, 6, 7, 8, 9 -> {
                (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2Px(context, 28f + 6)) / 3
            }
            else -> {
                ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2Px(context, 14f + 6)
            }
        }

        recyclerView.minimumHeight = recyclerViewHeight.toInt()

        val layoutManager = GridLayoutManager(context, gridSize)
        recyclerView.layoutManager = layoutManager
        val adapter = SugarAdapter.Builder.with(temp)
                .add(NineLatticeItemViewHolder::class.java) { holder -> holder.setWidth(itemWidth, data.link.weburl) }
                .build()
        recyclerView.adapter = adapter
        val count = recyclerView.itemDecorationCount
        if (count > 0) {
            for (index in 0 until count) {
                recyclerView.removeItemDecorationAt(index)
            }
        }
        recyclerView.addItemDecoration(SpacesItemDecoration(ScreenUtils.dp2PxInt(context, 7f)))

        adapter.notifyDataSetChanged()
    }
}