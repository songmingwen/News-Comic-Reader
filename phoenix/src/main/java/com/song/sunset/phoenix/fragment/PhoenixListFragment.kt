package com.song.sunset.phoenix.fragment

import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.base.AppConfig
import com.song.sunset.base.api.WholeApi
import com.song.sunset.base.bean.PageEntity
import com.song.sunset.base.fragment.BasePageLoadingFragment
import com.song.sunset.base.net.Net
import com.song.sunset.base.net.RetrofitCallback
import com.song.sunset.base.rxjava.RxUtil
import com.song.sunset.base.utils.AssetsUtils
import com.song.sunset.phoenix.holder.PhoenixHolderDispatcher
import com.song.sunset.phoenix.api.PhoenixNewsApi
import com.song.sunset.phoenix.bean.PhoenixChannelBean
import com.song.sunset.phoenix.bean.PhoenixNewsListBean
import com.song.sunset.utils.JsonUtil
import com.song.sunset.utils.ViewUtil
import com.zhihu.android.sugaradapter.SugarAdapter
import com.zhihu.android.sugaradapter.SugarHolder
import io.reactivex.Observable

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/10 16:57
 */
@Route(path = "/phoenix/list")
class PhoenixListFragment : BasePageLoadingFragment<PhoenixNewsListBean>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter.addDispatcher(PhoenixChannelBean::class.java, object : SugarAdapter.Dispatcher<PhoenixChannelBean>() {
            override fun dispatch(data: PhoenixChannelBean): Class<out SugarHolder<*>> {
                return PhoenixHolderDispatcher.dispatch(data)
            }
        })

    }

    override fun addHolders(builder: SugarAdapter.Builder): SugarAdapter.Builder {
        return PhoenixHolderDispatcher.addHolders(builder)
    }

    override fun onRefresh(fromUser: Boolean) {
        getDataFromRetrofit2(PhoenixNewsApi.DOWN, object : RetrofitCallback<PhoenixNewsListBean> {
            override fun onSuccess(data: PhoenixNewsListBean?) {
                if (data != null && data.data != null) {
                    postRefreshSucceed(data)
                } else {
                    postRefreshFailed(Throwable("data is fail"))
                }
            }

            override fun onFailure(errorCode: Int, errorMsg: String?) {
//                postRefreshFailed(Throwable(errorMsg))
                val json = AssetsUtils.getJson("default.json", activity)
                val bean = JsonUtil.gsonToBean(json, PhoenixNewsListBean::class.java)
                postRefreshSucceed(bean)
            }

        })
    }

    override fun onLoadMore(paging: PageEntity<PhoenixNewsListBean>?) {
        getDataFromRetrofit2(PhoenixNewsApi.UP, object : RetrofitCallback<PhoenixNewsListBean> {
            override fun onSuccess(data: PhoenixNewsListBean?) {
                if (data != null && data.data != null) {
                    postLoadMoreSucceed(data)
                    redirectPosition(mRecyclerView.height - ViewUtil.dip2px(7f) - ViewUtil.dip2px(60f))

                } else {
                    postLoadMoreFailed(Throwable("data is fail"))
                }
            }

            override fun onFailure(errorCode: Int, errorMsg: String?) {
//                postRefreshFailed(Throwable(errorMsg))
                val json = AssetsUtils.getJson("default.json", activity)
                val bean = JsonUtil.gsonToBean(json, PhoenixNewsListBean::class.java)
                postLoadMoreSucceed(bean)
                redirectPosition(mRecyclerView.height - ViewUtil.dip2px(7f) - ViewUtil.dip2px(60f))
            }

        })
    }

    override fun getScrollLoadMoreThreshold(): Int {
        return 1
    }

    private fun getDataFromRetrofit2(action: String, retrofitCallback: RetrofitCallback<PhoenixNewsListBean>) {
        val start = System.currentTimeMillis()
        val observable: Observable<List<PhoenixNewsListBean>> = Net
                .createService(PhoenixNewsApi::class.java, WholeApi.PHOENIX_NEWS_BASE_URL)
                .queryPhoenixListObservable(action, System.currentTimeMillis().toString())
        val end = System.currentTimeMillis()
        Log.i("time = ", (end - start).toString() + "millis")
        RxUtil.phoenixNewsSubscribe(observable, retrofitCallback)
    }

    private fun redirectPosition(dy: Int) {
        if (mRecyclerView == null) {
            return
        }
        mRecyclerView.postDelayed({ mRecyclerView.smoothScrollBy(0, dy, DecelerateInterpolator()) }, AppConfig.REFRESH_CLOSE_TIME.toLong())
    }

}