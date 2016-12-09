package com.song.sunset.model;

import com.song.core.base.CoreBaseModel;
import com.song.sunset.activitys.SunsetApplication;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.utils.retrofit.RetrofitApiBuilder;
import com.song.sunset.utils.service.RetrofitApi;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import rx.Observable;

/**
 * Created by songmw3 on 2016/12/8.
 */
public class ComicDetailModel implements CoreBaseModel {

    private ComicLocalCollectionDao comicLocalCollectionDao;

    public Observable<BaseBean<ComicDetailBean>> getData(int comicId) {
        return RetrofitApiBuilder.getRetrofitApi(RetrofitApi.class).queryComicDetailRDByGetObservable(comicId);
    }

    public boolean getCollectedState(int comicId) {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = SunsetApplication.getSunsetApplication().getDaoSession().getComicLocalCollectionDao();
        }
        return comicLocalCollectionDao.load((long) comicId) != null;
    }

    public boolean changeCollectedState(int comicId, ComicDetailBean comicDetailBean) {
        boolean isCollected = getCollectedState(comicId);
        if (isCollected) {
            comicLocalCollectionDao.deleteByKey((long) comicId);
        } else {
            ComicLocalCollection localCollection = new ComicLocalCollection();
            localCollection.setAuthor(comicDetailBean.getComic().getAuthor().getName());
            localCollection.setComicId(Long.parseLong(comicDetailBean.getComic().getComic_id()));
            localCollection.setCover(comicDetailBean.getComic().getCover());
            localCollection.setDescription(comicDetailBean.getComic().getDescription());
            localCollection.setName(comicDetailBean.getComic().getName());
            comicLocalCollectionDao.insert(localCollection);
        }
        return !isCollected;
    }
}
