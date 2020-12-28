package com.song.sunset.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.song.sunset.beans.PageItem
import java.util.HashMap

/**
 * @author songmingwen
 * @description
 * @since 2020/12/16
 */
class VP2PagerAdapter(private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager

    // 保存已经存在的 Fragment 实例
    private val fragments = HashMap<String, Fragment?>()

    private val pageItems = mutableListOf<PageItem>()

    fun setFragmentList(pageItems: MutableList<PageItem>) {
        clearItems()
        this.pageItems.addAll(pageItems)
        notifyDataSetChanged()
    }

    private fun clearItems() {
        pageItems.clear()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentManager.fragments.forEach {
            fragmentTransaction.remove(it)
        }
        fragmentTransaction.commitNowAllowingStateLoss()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pageItems.size
    }

    override fun createFragment(position: Int): Fragment {
        val title = getPageTitle(position)
        var fragment = fragments[title]
        val pagerItem = pageItems[position]
        if (fragment == null) {
//            fragment = Fragment.instantiate(fragmentActivity, pagerItem.fragmentClass.name, pagerItem.arguments)
            fragment = fragmentManager.fragmentFactory.instantiate(fragmentActivity.classLoader,pagerItem.fragmentClass.name)
            fragment.arguments = pagerItem.arguments
            fragments[title] = fragment
            Log.d("VP2PagerAdapter", "createFragment 新建 ${fragment::class.java}" + title)
        } else {
            Log.d("VP2PagerAdapter", "createFragment 缓存 ${fragment::class.java}" + title)
        }

        return fragment
    }

    fun getPageTitle(position: Int): String {
        return pageItems[position].title.toString()
    }

}