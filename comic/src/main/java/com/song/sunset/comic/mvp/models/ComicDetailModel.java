package com.song.sunset.comic.mvp.models;

import android.util.Base64;

import com.song.sunset.comic.bean.CollectionOnlineListBean;
import com.song.sunset.comic.bean.ComicDetailBean;
import com.song.sunset.comic.bean.ComicLocalCollection;
import com.song.sunset.base.bean.BaseBean;
import com.song.sunset.comic.utils.GreenDaoUtil;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.net.Net;
import com.song.sunset.comic.api.U17ComicApi;
import com.song.sunset.base.rxjava.RxUtil;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import io.reactivex.Observable;

/**
 * Created by Song on 2016/12/8.
 * E-mail:z53520@qq.com
 */
public class ComicDetailModel implements CoreBaseModel {

    public interface ChangeCollectionListener {
        void collected(boolean add);
    }

    private ComicLocalCollectionDao comicLocalCollectionDao;

    public Observable<BaseBean<ComicDetailBean>> getData(int comicId) {
        return Net.createService(U17ComicApi.class).queryComicDetailRDByObservable(comicId);
    }

    public boolean isCollected(int comicId) {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
        return comicLocalCollectionDao.load((long) comicId) != null;
    }

    /**
     * 从网络添加、删除漫画
     */
    public void changeCollectedStateFromNet(final ComicDetailBean bean, final ChangeCollectionListener changeCollectionListener) {
        Observable<BaseBean<CollectionOnlineListBean>> observable = Net.createService(U17ComicApi.class, ComicCollectionModel.getCollectionMap()).queryComicCollectionListRDByObservable(getPostData(bean));
        RxUtil.comicSubscribe(observable, new RetrofitCallback<CollectionOnlineListBean>() {
            @Override
            public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
                changeCollectionListener.collected(!isCollected(Integer.parseInt(bean.getComic().getComic_id())));
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                changeCollectionListener.collected(isCollected(Integer.parseInt(bean.getComic().getComic_id())));
            }
        });
    }

    /**
     * 从数据库添加、删除漫画
     */
    public boolean changeCollectedState(ComicDetailBean bean, boolean add) {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
        if (!add) {
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
        return add;
    }

    /**
     * 更新数据库，主要是更新已经收藏漫画的总章节数
     */
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

    /**
     * post参数
     * 135349|add|1513763388|1^
     * 135349|delete|1513763425|1^
     */
    private String getPostData(ComicDetailBean bean) {
        String postData = "";
        if (bean == null) {
            return postData;
        }
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
        boolean isCollected = isCollected(Integer.parseInt(bean.getComic().getComic_id()));
        String operation;
        if (isCollected) {
            operation = "delete";
        } else {
            operation = "add";
        }
        String all = bean.getComic().getComic_id() +
                "|" +
                operation +
                "|" +
                System.currentTimeMillis() +
                "|" +
                "1^";
        postData = Base64.encodeToString(all.getBytes(), Base64.DEFAULT);
        return postData;
    }
}
