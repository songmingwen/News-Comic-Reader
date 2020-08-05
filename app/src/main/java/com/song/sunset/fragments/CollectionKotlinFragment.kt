package com.song.sunset.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.viewmodel.CollectionViewModel
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
@Route(path = "/song/comic/collection")
class CollectionKotlinFragment : BaseFragment() {

    private var collectionViewModel: CollectionViewModel? = null

    private lateinit var adapter: CollectionComicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionViewModel = ViewModelProviders.of(this).get(CollectionViewModel::class.java)
        collectionViewModel!!.mLocalData.observe(this, Observer {
            collectionViewModel!!.getNewestCollectedComic()
            if (it != null && it.isNotEmpty()) {
                progress.showContent()
                adapter.setLocalData(it)
            }
        })
        collectionViewModel!!.mOnlineData.observe(this, Observer<List<ComicCollectionBean>> {
            progress.showContent()
            adapter.setOnlineList(it)
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
        adapter = CollectionComicAdapter(this.context!!)
        adapter.setOnItemClickListener { collectionViewModel?.save(it) }
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

