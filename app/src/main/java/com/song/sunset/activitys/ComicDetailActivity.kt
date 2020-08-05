package com.song.sunset.activitys

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.song.core.statusbar.StatusBarUtil
import com.song.sunset.R
import com.song.sunset.adapters.ComicDetailAdapter
import com.song.sunset.adapters.ComicDetailAdapter.COMIC_LIST_TYPE
import com.song.sunset.beans.ComicDetailBean
import com.song.sunset.utils.BitmapUtil
import com.song.sunset.utils.ViewUtil
import com.song.sunset.utils.fresco.FrescoUtil
import com.song.sunset.viewmodel.ComicDetailViewModel
import kotlinx.android.synthetic.main.activity_comic_detail_mvvm.*

@Route(path = "/song/comic/detail")
open class ComicDetailActivity : AppCompatActivity() {

    companion object {
        const val COMIC_ID = "comic_id"
    }

    var color = Color.WHITE
    private var comicId = -1
    private var adapter: ComicDetailAdapter? = null
    private var comicDetailBean: ComicDetailBean? = null
    private lateinit var vm: ComicDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏透明
        StatusBarUtil.setTransparent(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity_comic_detail_mvvm)

        initView()

        addListener()

        addObserver()

        vm.getComicDetailData(comicId)
        vm.getCollectionStatus(comicId)
    }

    private fun addObserver() {
        vm.comicDetailBean.observe(this, Observer {
            comicDetailBean = it
            toolbar.title = it.comic.name
            adapter?.setData(it)
            setExtractionColorFromBitmap(it)
            vm.updateCollectedComicData(it)
        })

        vm.dataStatus.observe(this, Observer {
            if (it) {
                progress.showContent()
                id_comic_detail_fab.visibility = View.VISIBLE
            } else {
                progress.showError {
                    progress.showLoading()
                    id_comic_detail_fab.visibility = View.GONE
                    vm.getComicDetailData(comicId)
                }
            }
        })

        vm.collectionStatus.observe(this, Observer {
            val res = if (it) android.R.drawable.star_big_on else android.R.drawable.star_big_off
            id_comic_detail_fab.setImageDrawable(resources.getDrawable(res))
        })

        vm.showTips.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun addListener() {
        var distance = 0
        id_comic_detail_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                distance += dy
                var rate = distance * 1.0f / ViewUtil.dip2px(200F)
                if (rate > 1) {
                    rate = 1F
                } else if (rate < 0) {
                    rate = 0F
                }
                toolbar.alpha = rate
            }
        })

        id_comic_detail_fab.setOnClickListener {
            vm.changeCollectedStatus(comicDetailBean!!)
        }
    }

    private fun initView() {
        vm = ViewModelProviders.of(this).get(ComicDetailViewModel::class.java)
        progress.showLoading()

        if (intent != null) {
            comicId = intent.getIntExtra(COMIC_ID, -1)
        }
        adapter = ComicDetailAdapter(this)
        id_comic_detail_recycler.adapter = adapter
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val itemViewType = adapter?.getItemViewType(position)
                return if (itemViewType == COMIC_LIST_TYPE) {
                    1
                } else {
                    3
                }
            }
        }
        id_comic_detail_recycler.layoutManager = layoutManager
    }

    private fun setExtractionColorFromBitmap(comicDetailRD: ComicDetailBean) {
        FrescoUtil.getCachedImageBitmap(FrescoUtil.getDataSource(comicDetailRD.comic.cover)!!, object : BaseBitmapDataSubscriber() {
            override fun onNewResultImpl(bitmap: Bitmap?) {
                if (this@ComicDetailActivity.isFinishing) return
                color = BitmapUtil.getColorFromBitmap(bitmap)
                Handler().post { adapter?.setColor(color) }
                toolbar.setBackgroundColor(color)
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {

            }
        })
    }
}
