package com.song.sunset.model;

import com.song.core.base.CoreBaseModel;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.ComicApi;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import rx.Observable;

/**
 * Created by songmw3 on 2016/12/8.
 */
public class ComicDetailModel implements CoreBaseModel {

    private ComicLocalCollectionDao comicLocalCollectionDao;

    public Observable<BaseBean<ComicDetailBean>> getData(int comicId) {
        return RetrofitService.createApi(ComicApi.class).queryComicDetailRDByGetObservable(comicId);
    }

    public boolean getCollectedState(int comicId) {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
        return comicLocalCollectionDao.load((long) comicId) != null;
    }

    public boolean changeCollectedState(ComicDetailBean comicDetailBean) {
        boolean isCollected = getCollectedState(Integer.parseInt(comicDetailBean.getComic().getComic_id()));
        if (isCollected) {
            comicLocalCollectionDao.deleteByKey((long) Integer.parseInt(comicDetailBean.getComic().getComic_id()));
        } else {
            ComicLocalCollection localCollection = new ComicLocalCollection();
            localCollection.setAuthor(comicDetailBean.getComic().getAuthor().getName());
            localCollection.setComicId(Long.parseLong(comicDetailBean.getComic().getComic_id()));
            localCollection.setCover(comicDetailBean.getComic().getCover());
            localCollection.setDescription(comicDetailBean.getComic().getDescription());
            localCollection.setName(comicDetailBean.getComic().getName());
            localCollection.setChapterNum(String.valueOf(comicDetailBean.getChapter_list().size()));
            comicLocalCollectionDao.insert(localCollection);
        }
        return !isCollected;
    }

    public void updateCollectedComicData(ComicDetailBean comicDetailBean) {
        if (!getCollectedState(Integer.parseInt(comicDetailBean.getComic().getComic_id()))) return;
        ComicLocalCollection localCollection = new ComicLocalCollection();
        localCollection.setAuthor(comicDetailBean.getComic().getAuthor().getName());
        localCollection.setComicId(Long.parseLong(comicDetailBean.getComic().getComic_id()));
        localCollection.setCover(comicDetailBean.getComic().getCover());
        localCollection.setDescription(comicDetailBean.getComic().getDescription());
        localCollection.setName(comicDetailBean.getComic().getName());
        localCollection.setChapterNum(String.valueOf(comicDetailBean.getChapter_list().size()));
        comicLocalCollectionDao.insertOrReplace(localCollection);
    }
}
