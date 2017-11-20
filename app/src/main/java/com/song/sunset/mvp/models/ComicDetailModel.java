package com.song.sunset.mvp.models;

import com.song.core.base.CoreBaseModel;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.api.U17ComicApi;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import rx.Observable;

/**
 * Created by Song on 2016/12/8.
 * E-mail:z53520@qq.com
 */
public class ComicDetailModel implements CoreBaseModel {

    private ComicLocalCollectionDao comicLocalCollectionDao;

    public Observable<BaseBean<ComicDetailBean>> getData(int comicId) {
        return RetrofitService.createApi(U17ComicApi.class).queryComicDetailRDByGetObservable(comicId);
    }

    public boolean isCollected(int comicId) {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
        return comicLocalCollectionDao.load((long) comicId) != null;
    }

    public boolean changeCollectedState(ComicDetailBean bean) {
        if (bean == null) {
            return false;
        }
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
        boolean isCollected = isCollected(Integer.parseInt(bean.getComic().getComic_id()));
        if (isCollected) {
            comicLocalCollectionDao.deleteByKey((long) Integer.parseInt(bean.getComic().getComic_id()));
        } else {
            ComicLocalCollection localCollection = new ComicLocalCollection();
            localCollection.setAuthor(bean.getComic().getAuthor().getName());
            localCollection.setComicId(Long.parseLong(bean.getComic().getComic_id()));
            localCollection.setCover(bean.getComic().getCover());
            localCollection.setDescription(bean.getComic().getDescription());
            localCollection.setName(bean.getComic().getName());
            localCollection.setChapterNum(String.valueOf(bean.getChapter_list().size()));
            comicLocalCollectionDao.insert(localCollection);
        }
        return !isCollected;
    }

    public void updateCollectedComicData(ComicDetailBean bean) {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
        if (!isCollected(Integer.parseInt(bean.getComic().getComic_id()))) return;
        ComicLocalCollection localCollection = new ComicLocalCollection();
        localCollection.setAuthor(bean.getComic().getAuthor().getName());
        localCollection.setComicId(Long.parseLong(bean.getComic().getComic_id()));
        localCollection.setCover(bean.getComic().getCover());
        localCollection.setDescription(bean.getComic().getDescription());
        localCollection.setName(bean.getComic().getName());
        localCollection.setChapterNum(String.valueOf(bean.getChapter_list().size()));
        comicLocalCollectionDao.insertOrReplace(localCollection);
    }
}
