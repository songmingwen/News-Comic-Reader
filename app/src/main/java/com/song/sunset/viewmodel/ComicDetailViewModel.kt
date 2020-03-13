package com.song.sunset.viewmodel

import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.song.sunset.beans.CollectionOnlineListBean
import com.song.sunset.beans.ComicDetailBean
import com.song.sunset.beans.ComicLocalCollection
import com.song.sunset.mvp.models.ComicCollectionModel
import com.song.sunset.utils.GreenDaoUtil
import com.song.sunset.utils.api.U17ComicApi
import com.song.sunset.utils.retrofit.RetrofitCallback
import com.song.sunset.utils.retrofit.RetrofitFactory
import com.song.sunset.utils.rxjava.RxUtil
import com.sunset.greendao.gen.ComicLocalCollectionDao
import io.reactivex.disposables.CompositeDisposable

open class ComicDetailViewModel : ViewModel() {

    var comicDetailBean = MutableLiveData<ComicDetailBean>()
    var dataStatus = MutableLiveData<Boolean>()
    var collectionStatus = MutableLiveData<Boolean>()
    var showTips = MutableLiveData<String>()

    private var comicLocalCollectionDao: ComicLocalCollectionDao? = GreenDaoUtil.getDaoSession().comicLocalCollectionDao

    private var dispose = CompositeDisposable()

    fun getComicDetailData(comicId: Int) {
        val dis = RxUtil.comic(
                RetrofitFactory.createApi(U17ComicApi::class.java).queryComicDetailRDByObservable(comicId),
                object : RetrofitCallback<ComicDetailBean> {
                    override fun onSuccess(t: ComicDetailBean?) {
                        comicDetailBean.value = t
                        dataStatus.value = true
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String?) {
                        dataStatus.value = false
                    }

                })
        dispose.add(dis)
    }

    fun getCollectionStatus(comicId: Int): Boolean {
        val status = comicLocalCollectionDao?.load(comicId.toLong()) != null
        collectionStatus.value = status
        return status
    }

    fun changeCollectedStatus(bean: ComicDetailBean) {
        val dis = RxUtil.comic(RetrofitFactory.createApi(U17ComicApi::class.java, ComicCollectionModel.getCollectionMap()).queryComicCollectionListRDByObservable(getPostData(bean)),
                object : RetrofitCallback<CollectionOnlineListBean> {
                    override fun onSuccess(t: CollectionOnlineListBean?) {
                        val collected = !getCollectionStatus(Integer.parseInt(bean.comic.comic_id))
                        collectionStatus.value = collected
                        showTips.value = if (collected) "收藏成功" else "取消收藏"

                        changeLocalCollectedStatus(bean, collected)
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String?) {
                        val collected = getCollectionStatus(Integer.parseInt(bean.comic.comic_id))
                        showTips.value = if (collected) "取消收藏失败" else "收藏失败"
                    }
                })
        dispose.add(dis)
    }

    /**
     * 更新数据库，主要是更新已经收藏漫画的总章节数
     */
    fun updateCollectedComicData(bean: ComicDetailBean) {
        if (!getCollectionStatus(Integer.parseInt(bean.comic.comic_id))) return
        val localCollection = ComicLocalCollection()
        localCollection.author = bean.comic.author.name
        localCollection.comicId = java.lang.Long.parseLong(bean.comic.comic_id)
        localCollection.cover = bean.comic.cover
        localCollection.description = bean.comic.description
        localCollection.name = bean.comic.name
        localCollection.chapterNum = bean.chapter_list.size.toString()
        comicLocalCollectionDao?.insertOrReplace(localCollection)
    }

    /**
     * 从数据库添加、删除漫画
     */
    private fun changeLocalCollectedStatus(bean: ComicDetailBean, collected: Boolean) {
        if (collected) {
            val localBean = ComicLocalCollection()
            bean.apply {
                localBean.author = comic.author.name
                localBean.comicId = comic.comic_id.toLong()
                localBean.cover = comic.cover
                localBean.description = comic.description
                localBean.name = comic.name
                localBean.chapterNum = chapter_list.size.toString()
                comicLocalCollectionDao?.insert(localBean)
            }
        } else {
            comicLocalCollectionDao?.deleteByKey(bean.comic.comic_id.toLong())
        }
    }

    /**
     * post参数
     * 135349|add|1513763388|1^
     * 135349|delete|1513763425|1^
     */
    private fun getPostData(bean: ComicDetailBean?): String {
        var postData = ""
        if (bean == null) {
            return postData
        }
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().comicLocalCollectionDao
        }
        val isCollected = getCollectionStatus(Integer.parseInt(bean.comic.comic_id))
        val operation: String
        operation = if (isCollected) {
            "delete"
        } else {
            "add"
        }
        val all = bean.comic.comic_id +
                "|" +
                operation +
                "|" +
                System.currentTimeMillis() +
                "|" +
                "1^"
        postData = Base64.encodeToString(all.toByteArray(), Base64.DEFAULT)
        return postData
    }

    override fun onCleared() {
        dispose.clear()
    }
}
