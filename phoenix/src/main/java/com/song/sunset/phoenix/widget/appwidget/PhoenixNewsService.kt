package com.song.sunset.phoenix.widget.appwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import coil.imageLoader
import coil.request.ImageRequest
import coil.target.Target
import com.song.sunset.base.AppConfig
import com.song.sunset.base.api.WholeApi
import com.song.sunset.base.net.Net
import com.song.sunset.base.net.RetrofitCallback
import com.song.sunset.base.rxjava.RxUtil
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.api.PhoenixNewsApi
import com.song.sunset.phoenix.bean.PhoenixNewsListBean
import com.song.sunset.phoenix.bean.WidgetItem
import com.song.sunset.utils.BitmapUtil
import io.reactivex.Observable
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * Desc:    控件服务，可以做耗时操作
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/3/9 17:45
 */
class PhoenixNewsService : RemoteViewsService() {

    companion object {
        const val EXTRA_BUNDLE: String = "song.sunset.phoenix.extra.bundle"
        const val EXTRA: String = "song.sunset.phoenix.extra"
    }

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        Log.i(PhoenixNewsWidget.TAG, "onGetViewFactory")
        return PhoenixWidgetFactory(this.applicationContext, intent)
    }

    class PhoenixWidgetFactory(context: Context, intent: Intent?) : RemoteViewsFactory {

        private val mContext = context

        private val mIntent: Intent? = intent

        private var mAppWidgetId: Int = 0

        private lateinit var mList: ArrayList<WidgetItem>

        override fun onCreate() {
            Log.i(PhoenixNewsWidget.TAG, "onCreate")

            mAppWidgetId = mIntent?.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0) ?: 0

            mList = ArrayList()

            fetchData()
        }

        override fun getViewAt(position: Int): RemoteViews {
            Log.i(PhoenixNewsWidget.TAG, "getViewAt widgetItem = " + mList[position].toString())
            return RemoteViews(AppConfig.getApp().packageName, R.layout.item_phoenix_news_widget)
                    .apply {
                        setTextViewText(R.id.title, mList[position].title)
                        val bitmap = BitmapUtil.byteToBitmap(mList[position].bitmap)
                        setImageViewBitmap(R.id.image, bitmap)
                        val fillInIntent = Intent().apply {
                            Bundle().also { extras ->
                                extras.putSerializable(EXTRA, mList[position])
                                putExtra(EXTRA_BUNDLE, extras)
                            }
                        }

                        setOnClickFillInIntent(R.id.container, fillInIntent)
                    }
        }

        override fun onDataSetChanged() {
            Log.i(PhoenixNewsWidget.TAG, "onDataSetChanged")
            fetchData()
        }

        override fun onDestroy() {}

        override fun getCount(): Int {
            return mList.size
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        private fun fetchData() {
            getDataFromRetrofit2(object : RetrofitCallback<PhoenixNewsListBean> {
                override fun onSuccess(data: PhoenixNewsListBean?) {
                    mList.clear()

                    Log.i(PhoenixNewsWidget.TAG, "fetchData onSuccess")

                    data?.item?.forEach {
                        val item = WidgetItem()
                        item.title = it.title
                        item.image = it.thumbnail
                        item.jumpUrl = it.link.weburl
                        item.className = "com.song.sunset.activitys.PhoenixNewsActivity"
                        mList.add(item)
                        getBitmapFromNet(mContext, item.image)
                    }
                }

                override fun onFailure(errorCode: Int, errorMsg: String?) {
                    Log.i(PhoenixNewsWidget.TAG, "fetchData onFailure")
                }

            })
        }

        private fun getDataFromRetrofit2(retrofitCallback: RetrofitCallback<PhoenixNewsListBean>) {
            val observable: Observable<List<PhoenixNewsListBean>> = Net
                    .createService(PhoenixNewsApi::class.java, WholeApi.PHOENIX_NEWS_BASE_URL)
                    .queryPhoenixListObservable(PhoenixNewsApi.UP, System.currentTimeMillis().toString())
            RxUtil.phoenixNewsSubscribe(observable, retrofitCallback)
        }

        private fun getBitmapFromNet(context: Context, url: String) {
            val request = ImageRequest.Builder(context)
                    .data(url)
                    .target(object : Target {
                        override fun onSuccess(result: Drawable) {
                            Log.i(PhoenixNewsWidget.TAG, "getBitmapFromNet onSuccess")
                            if (result is BitmapDrawable) {
                                val bitmap = result.bitmap
                                val byteArray = BitmapUtil.bitmapToByte(bitmap)
                                byteArray?.also { byte ->
                                    mList.filter { url == it.image }
                                            .forEach { it.bitmap = byte }
                                }
                            }
                        }
                    })
                    .build()
            context.imageLoader.enqueue(request)
        }
    }
}