package com.song.sunset.comic.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import com.song.sunset.comic.ComicListActivity
import com.song.sunset.comic.bean.ComicListBean
import com.song.sunset.base.bean.PageEntity
import com.song.sunset.base.fragment.BasePageLoadingFragment
import com.song.sunset.comic.api.U17ComicApi
import com.song.sunset.base.net.Net
import com.song.sunset.comic.holders.ComicItemViewHolder
import com.zhihu.android.sugaradapter.SugarAdapter.Builder

/**
 * @author songmingwen
 * @description
 * @since 2020/12/16
 */
open class ComicBaseListFragment : BasePageLoadingFragment<ComicListBean>() {

    private var argName = ""
    private var argValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            argName = requireArguments().getString(ComicListActivity.ARG_NAME) ?: ""
            argValue = requireArguments().getInt(ComicListActivity.ARG_VALUE)
        }
        if (TextUtils.isEmpty(argName)) {
            //每日更新的标识
            argName = "sort"
            argValue = 0
        }
    }

    override fun addHolders(builder: Builder): Builder {
        return builder.add(ComicItemViewHolder::class.java)
    }

    @SuppressLint("CheckResult")
    override fun onRefresh(fromUser: Boolean) {
        Net.createService(U17ComicApi::class.java).queryComicList(1, argName, argValue)
                .compose(bindLifecycleAndScheduler())
                .subscribe({
                    if (it != null && it.isSuccessful) {
                        val comicListBean = it.body()?.data?.returnData
                        if (comicListBean != null) {
                            postRefreshSucceed(comicListBean)
                        } else {
                            postRefreshFailed(it.errorBody())
                        }
                    }
                }, {
                    postRefreshFailed(it)
                })
    }

    @SuppressLint("CheckResult")
    override fun onLoadMore(paging: PageEntity<ComicListBean>?) {
        val pagePosition = if (paging == null) 1 else paging.currentPage + 1
        Net.createService(U17ComicApi::class.java).queryComicList(pagePosition, argName, argValue)
                .compose(bindLifecycleAndScheduler())
                .subscribe({
                    if (it != null && it.isSuccessful) {
                        val comicListBean = it.body()?.data?.returnData
                        if (comicListBean != null) {
                            postLoadMoreSucceed(comicListBean)
                        } else {
                            postLoadMoreFailed(it.errorBody())
                        }
                    }
                }, {
                    postLoadMoreFailed(it)
                })
    }

}