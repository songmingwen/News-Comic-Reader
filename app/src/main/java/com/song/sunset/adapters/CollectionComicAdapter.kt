package com.song.sunset.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.song.sunset.R
import com.song.sunset.activitys.ComicDetailActivity
import com.song.sunset.beans.ComicCollectionBean
import com.song.sunset.beans.ComicLocalCollection
import com.song.sunset.holders.ComicListViewHolder
import com.song.sunset.utils.fresco.FrescoUtil
import java.util.*

/**
 * Created by z5352_000 on 2016/10/29 0029.
 * E-mail:z53520@qq.com
 */
class CollectionComicAdapter(private val context: Context) : RecyclerView.Adapter<ComicListViewHolder>() {

    companion object {
        const val NO_MORE = "无更新"
    }

    private var mLocalData: MutableList<ComicLocalCollection> = ArrayList()

    private var mOnlineData: MutableList<ComicCollectionBean> = ArrayList()

    private var mShowData: MutableList<ComicCollectionBean> = ArrayList()

    private var mOnItemClickListener: ((comicId: String) -> Unit?)? = null

    fun setOnItemClickListener(onItemClickListener: (comicId: String) -> Unit) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicListViewHolder {
        return ComicListViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_list_sample_item, parent, false))
    }

    override fun onBindViewHolder(holder: ComicListViewHolder, position: Int) {
        if (mShowData.isNotEmpty()) {
            val data = mShowData[position]
            holder.haveUpdate.apply {
                if (TextUtils.equals(data.updateInfo, NO_MORE)) {
                    this.visibility = View.GONE
                } else {
                    this.visibility = View.VISIBLE
                    this.text = data.updateInfo
                }
            }
            holder.comicName.text = data.name
            FrescoUtil.setFrescoImage(holder.simpleDraweeView, data.cover)

            holder.itemView.setOnClickListener {
                mOnItemClickListener?.invoke(data.comic_id)
                ARouter.getInstance().build("/song/comic/detail").withInt(ComicDetailActivity.COMIC_ID, data.comic_id.toInt()).navigation()
            }
        }
    }

    override fun getItemCount(): Int {
        return when {
            mOnlineData.isNotEmpty() -> {
                mOnlineData.size
            }
            mLocalData.isNotEmpty() -> {
                mLocalData.size
            }
            else -> {
                0
            }
        }
    }

    fun setLocalData(data: List<ComicLocalCollection>?) {
        if (data != null && data.isNotEmpty()) {
            if (mLocalData.size > 0) {
                mLocalData.clear()
            }
            val newData = data.sortedBy { it.name }
            mLocalData.addAll(newData)
            fillShowData()
        }
    }

    fun setOnlineList(collectionList: List<ComicCollectionBean>?) {
        if (collectionList == null || collectionList.isEmpty()) {
            return
        }
        //按照漫画 id 排序一下，以免出现加载 local 再加载 online 后出现闪动的问题。
        val newCollectionList = collectionList.sortedBy { it.name }
        mOnlineData.clear()
        mOnlineData.addAll(newCollectionList)
        fillShowData()
    }

    private fun fillShowData() {
        if (mOnlineData.isNotEmpty()) {
            if (mShowData.isNotEmpty()) {
                mShowData.clear()
            }
            mShowData.addAll(mOnlineData)
            //处理 update 数据
            if (mLocalData.isNotEmpty()) {
                // 本地有记录的对比阅读章节数
                mLocalData.forEach { bean -> setUpdateInfo(bean) }
                // 本地没有记录的线上数据统一按照一篇没读算
                for (data in mShowData) {
                    if (!TextUtils.equals(data.updateInfo, NO_MORE)) {
                        data.updateInfo = String.format(context.getString(R.string.have_update),
                                data.pass_chapter_num)
                    }
                }
            } else {
                mShowData.forEach { bean ->
                    bean.updateInfo = String.format(context.getString(R.string.have_update), bean.pass_chapter_num)
                }
            }
        }
        //如果 showData 为空，说明 online 数据没回来，或者 online 数据为空
        if (mShowData.isEmpty() && mLocalData.isNotEmpty()) {
            mLocalData.forEach {
                val bean: ComicCollectionBean = ComicCollectionBean()
                bean.comic_id = it.comicId.toString()
                bean.name = it.name
                bean.cover = it.cover
                bean.author_name = it.author
                bean.pass_chapter_num = it.chapterNum.toInt()
                bean.updateInfo = NO_MORE
                mShowData.add(bean)
            }
        }

        notifyDataSetChanged()
    }

    private fun setUpdateInfo(bean: ComicLocalCollection) {
        if (mShowData.isEmpty()) {
            return
        }
        for (data in mShowData) {
            if (TextUtils.equals(data.comic_id, bean.comicId.toString())) {
                if ((data.pass_chapter_num - bean.chapterNum.toInt()) > 0) {
                    data.updateInfo = String.format(context.getString(R.string.have_update),
                            data.pass_chapter_num - bean.chapterNum.toInt())
                } else {
                    data.updateInfo = NO_MORE
                }
            }
        }
    }

}