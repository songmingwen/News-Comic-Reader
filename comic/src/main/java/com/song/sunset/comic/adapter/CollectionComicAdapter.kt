package com.song.sunset.comic.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.song.sunset.comic.R
import com.song.sunset.comic.bean.ComicCollectionBean
import com.song.sunset.comic.bean.ComicLocalCollection
import com.song.sunset.comic.holders.ComicListViewHolder
import com.song.sunset.utils.FrescoUtil
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
                ARouter.getInstance().build("/song/comic/detail").withInt("comic_id", data.comic_id.toInt()).navigation()
            }
        }
    }

    override fun getItemCount(): Int {
        return when {
            mShowData.isNotEmpty() -> {
                mShowData.size
            }
            else -> {
                0
            }
        }
    }

    /**
     * 设置本地数据库数据
     */
    fun setLocalData(data: List<ComicLocalCollection>?) {
        if (data == null || data.isEmpty()) {
            return
        }
        //按照漫画 id 排序一下，以免出现加载 local 再加载 online 后出现闪动的问题。
        val newData = data.sortedBy { it.name }
        mLocalData.clear()
        mLocalData.addAll(newData)

        mShowData.clear()
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
        notifyDataSetChanged()
    }

    /**
     * 设置网络接口数据
     */
    fun setOnlineList(collectionList: List<ComicCollectionBean>?) {
        if (collectionList == null || collectionList.isEmpty()) {
            return
        }
        //按照漫画 id 排序一下，以免出现加载 local 再加载 online 后出现闪动的问题。
        val newCollectionList = collectionList.sortedBy { it.name }
        mOnlineData.clear()
        mOnlineData.addAll(newCollectionList)

        mShowData.clear()
        mShowData.addAll(mOnlineData)

        mShowData.forEach { bean -> setUpdateInfo(bean) }

        notifyDataSetChanged()
    }

    private fun setUpdateInfo(showBean: ComicCollectionBean) {
        var match = false
        mLocalData.forEach { localBean ->
            run {
                if (TextUtils.equals(localBean.comicId.toString(), showBean.comic_id)) {
                    match = true
                    //如果本地已经有对应漫画

                    if (showBean.pass_chapter_num - localBean.chapterNum.toInt() > 0) {
                        showBean.updateInfo = String.format(context.getString(R.string.have_update),
                                showBean.pass_chapter_num - localBean.chapterNum.toInt())
                    } else {
                        showBean.updateInfo = NO_MORE
                    }
                }
            }
        }

        //如果本地没有对应的漫画信息
        if (!match) {
            showBean.updateInfo = String.format(context.getString(R.string.have_update), showBean.pass_chapter_num)
        }
    }

}