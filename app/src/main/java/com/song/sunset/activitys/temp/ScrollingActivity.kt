package com.song.sunset.activitys.temp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.song.sunset.R
import com.song.sunset.activitys.ComicListActivity
import com.song.sunset.adapters.VP2PagerAdapter
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.base.net.Net
import com.song.sunset.base.net.RetrofitCallback
import com.song.sunset.base.rxjava.RxUtil
import com.song.sunset.comic.bean.ComicRankListBean
import com.song.sunset.beans.PageItem
import com.song.sunset.beans.User
import com.song.sunset.fragments.ComicBaseListFragment
import com.song.sunset.holders.HeaderHolder
import com.song.sunset.comic.api.U17ComicApi
import com.zhihu.android.sugaradapter.SugarAdapter
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : BaseActivity() {

    companion object {
        private const val BUNDLE_KEY_PAGE_INDEX = "page_index"
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, ScrollingActivity::class.java))
        }
    }

    private var rankingPagerAdapter: VP2PagerAdapter? = null
    private var mCurrPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        initHeader()
        loadNetData()
    }

    private fun initHeader() {
        val list = ArrayList<Any>()
        list.add(User("宋先生", "银河系-太阳系-地球-中国", "1008610010"))
        list.add(User("沈女士", "银河系-太阳系-地球-中国", "1008610010"))
        list.add(User("宋儿子", "银河系-太阳系-地球-中国", "1008610010"))
        val adapter = SugarAdapter.Builder.with(list).add(HeaderHolder::class.java).build()
        header_rv.adapter = adapter
        header_rv.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewPager(pageItems: MutableList<PageItem>) {
        rankingPagerAdapter = VP2PagerAdapter(this)
        rankingPagerAdapter!!.setFragmentList(pageItems)
        ranking_view_pager.adapter = rankingPagerAdapter
        ranking_view_pager.offscreenPageLimit = 1
        ranking_view_pager.currentItem = mCurrPos
        ranking_view_pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                mCurrPos = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        val tabLayoutMediator = TabLayoutMediator(tabLayout, ranking_view_pager, false
        ) { tab: TabLayout.Tab, position: Int -> tab.text = rankingPagerAdapter!!.getPageTitle(position) }
        tabLayoutMediator.attach()
    }

    private fun loadNetData() {
        val observable = Net.createService(U17ComicApi::class.java).queryComicRankListBeanByObservable()
        RxUtil.comicSubscribe(observable, object : RetrofitCallback<ComicRankListBean?> {
            override fun onSuccess(comicRankListBean: ComicRankListBean?) {
                val rankingTypeItemList = comicRankListBean?.rankinglist
                val pageItems: MutableList<PageItem> = ArrayList()
                if (rankingTypeItemList != null) {
                    for (rankingTypeItem in rankingTypeItemList) {
                        val bundle = Bundle()
                        bundle.putString(ComicListActivity.ARG_NAME, rankingTypeItem.argName)
                        bundle.putInt(ComicListActivity.ARG_VALUE, rankingTypeItem.argValue.toInt())
                        pageItems.add(PageItem(ComicBaseListFragment::class.java, rankingTypeItem.title, bundle))
                    }
                }
                initViewPager(pageItems)
            }

            override fun onFailure(errorCode: Int, errorMsg: String) {}
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(BUNDLE_KEY_PAGE_INDEX, mCurrPos)
    }

}