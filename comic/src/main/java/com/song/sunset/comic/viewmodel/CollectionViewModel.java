package com.song.sunset.comic.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import com.song.sunset.base.bean.BaseBean;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.viewmodel.BaseViewModel;
import com.song.sunset.base.net.Net;
import com.song.sunset.base.rxjava.RxUtil;
import com.song.sunset.comic.api.U17ComicApi;
import com.song.sunset.comic.bean.CollectionOnlineListBean;
import com.song.sunset.comic.bean.ComicCollectionBean;
import com.song.sunset.comic.bean.ComicLocalCollection;
import com.song.sunset.comic.utils.GreenDaoUtil;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import java8.util.Optional;
import java8.util.stream.StreamSupport;

/**
 * @author songmingwen
 * @description
 * @since 2019/1/10
 */
public class CollectionViewModel extends BaseViewModel {

    public MutableLiveData<List<ComicLocalCollection>> mLocalData = new MutableLiveData<>();
    public MutableLiveData<List<ComicCollectionBean>> mOnlineData = new MutableLiveData<>();
    private static ComicLocalCollectionDao comicLocalCollectionDao;

    private CollectionOnlineListBean mCollectionOnlineListBean;

    static {
        comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
    }

    public CollectionViewModel(@NotNull Application application) {
        super(application);
    }

    public void getNewestCollectedComic() {
        Observable<BaseBean<CollectionOnlineListBean>> observable = Net.createService(U17ComicApi.class).queryComicCollectionListRDByObservable("");
        RxUtil.comicSubscribe(observable, new RetrofitCallback<CollectionOnlineListBean>() {
            @Override
            public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
                mCollectionOnlineListBean = collectionOnlineListBean;
                mOnlineData.postValue(collectionOnlineListBean.getFavList());
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {

            }
        });
    }

    public void getLocalCollectedComic() {
        createDao();
        mLocalData.postValue(comicLocalCollectionDao.loadAll());
    }

    public void save(String id) {
        if (mCollectionOnlineListBean == null) {
            return;
        }
        Optional.ofNullable(mCollectionOnlineListBean.getFavList())
                .stream()
                .flatMap(StreamSupport::stream)
                .filter(bean -> TextUtils.equals(bean.getComic_id(), id))
                .findFirst()
                .ifPresent(this::saveCollectedComic);
    }

    private boolean saveCollectedComic(ComicCollectionBean bean) {
        if (bean == null)
            return false;

        GreenDaoUtil.getDb().beginTransaction();
        createDao();
        ComicLocalCollection localCollection = new ComicLocalCollection();
        localCollection.setAuthor(bean.getAuthor_name());
        localCollection.setComicId(Long.parseLong(bean.getComic_id()));
        localCollection.setCover(bean.getCover());
        localCollection.setName(bean.getName());
        localCollection.setChapterNum(String.valueOf(bean.getPass_chapter_num()));
        comicLocalCollectionDao.insertOrReplace(localCollection);
        GreenDaoUtil.getDb().setTransactionSuccessful();
        GreenDaoUtil.getDb().endTransaction();
        return true;
    }

    private void createDao() {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
    }

    @NonNull
    private Map<String, String> getCollectionMap() {
        Map<String, String> map = new HashMap<>();
        map.put("t", System.currentTimeMillis() + "");
        return map;
    }

}
