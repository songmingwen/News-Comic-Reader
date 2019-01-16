package com.song.sunset.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.song.sunset.CollectionViewModel
import com.song.sunset.R
import com.song.sunset.adapters.CollectionComicAdapter
import com.song.sunset.beans.ComicCollectionBean
import com.song.sunset.fragments.base.BaseFragment
import com.song.sunset.utils.ViewUtil
import kotlinx.android.synthetic.main.fragment_comic_collection.*

/**
 * @author songmingwen
 * @description
 * @since 2019/1/15
 */

class CollectionKotlinFragment : BaseFragment() {

    private var collectionViewModel: CollectionViewModel? = null

    private var adapter: CollectionComicAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionViewModel = ViewModelProviders.of(this).get(CollectionViewModel::class.java)
        collectionViewModel!!.localCollectedLiveData.observe(this, Observer {
            collectionViewModel!!.getNewestCollectedComic()
            if (adapter != null && it != null && !it.isEmpty()) {
                progress.showContent()
                adapter!!.setData(it)
            }
        })
        collectionViewModel!!.collectedLiveData.observe(this, Observer<List<ComicCollectionBean>> {
            if (adapter != null) {
                progress.showContent()
                adapter!!.setCollectionList(it)
            }
            if (it == null || it.isEmpty()) {
                progress.showEmpty()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comic_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progress.showLoading()
        adapter = CollectionComicAdapter(activity)
        id_comic_collection.adapter = adapter
        id_comic_collection.layoutManager = object : GridLayoutManager(activity, 3) {
            override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
                return ViewUtil.getScreenHeigth()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        collectionViewModel!!.getLocalCollectedComic()
    }

}

