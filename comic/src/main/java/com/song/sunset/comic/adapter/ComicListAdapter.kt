package com.song.sunset.comic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alibaba.android.arouter.launcher.ARouter
import com.song.sunset.base.adapter.BaseRecyclerViewAdapter
import com.song.sunset.comic.R
import com.song.sunset.comic.bean.ComicsBean
import com.song.sunset.comic.holders.ComicListViewHolder
import com.song.sunset.utils.ViewUtil

/**
 * Created by Song on 2016/8/27 0027.
 * Email:z53520@qq.com
 */
class ComicListAdapter(private val context: Context) : BaseRecyclerViewAdapter<ComicsBean?, ComicListViewHolder?>() {
    override fun onCreatePersonalViewHolder(parent: ViewGroup, viewType: Int): ComicListViewHolder {
        return ComicListViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_list_full_item, parent, false))
    }

    override fun onBindPersonalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comicsBean = data[position]!!
        val comicListViewHolder = holder as ComicListViewHolder
        val realWidth = ViewUtil.dip2px(113f)
        val realHeight = ViewUtil.dip2px(143f)
        comicListViewHolder.comicName.text = comicsBean.name
        comicListViewHolder.comicDesc.text = comicsBean.description
        comicListViewHolder.comicAuthor.text = comicsBean.author
        comicListViewHolder.comicTags.text = getTags(comicsBean)

        comicListViewHolder.simpleDraweeView.visibility = View.GONE
        comicListViewHolder.cover.visibility = View.VISIBLE
        comicListViewHolder.cover.load(comicsBean.cover)
//        Glide.with(context).load(comicsBean.getCover()).into(comicListViewHolder.cover);
//        FrescoUtil.setFrescoCoverImage(comicListViewHolder.simpleDraweeView, comicsBean.getCover(), realWidth, realHeight);
    }

    private fun getTags(comicsBean: ComicsBean): StringBuffer {
        val stringBuffer = StringBuffer()
        for (tag in comicsBean.tags) {
            stringBuffer.append(tag).append(" | ")
        }
        stringBuffer.delete(stringBuffer.length - 3, stringBuffer.length - 1)
        return stringBuffer
    }

    override fun onItemClick(view: View, position: Int) {
        ARouter.getInstance().build("/comic/detail").withInt("comic_id", data[position]!!.comicId).navigation()
    }

}