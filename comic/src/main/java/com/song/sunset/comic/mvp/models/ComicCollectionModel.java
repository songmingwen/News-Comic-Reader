package com.song.sunset.comic.mvp.models;

import com.song.sunset.base.bean.BaseBean;
import com.song.sunset.base.net.Net;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.rxjava.RxUtil;
import com.song.sunset.comic.api.U17ComicApi;
import com.song.sunset.comic.bean.CollectionOnlineListBean;
import com.song.sunset.comic.mvp.views.ComicCollectionView;

import io.reactivex.Observable;


/**
 * Created by Song on 2017/12/22 0022.
 * E-mail: z53520@qq.com
 */

public class ComicCollectionModel {

    public void getNewestCollectedComic(final ComicCollectionView view) {
        Observable<BaseBean<CollectionOnlineListBean>> observable = Net.INSTANCE.createService(U17ComicApi.class).queryComicCollectionListRDByObservable("");
        RxUtil.comicSubscribe(observable, new RetrofitCallback<CollectionOnlineListBean>() {
            @Override
            public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
                view.onSuccess(collectionOnlineListBean);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                view.onFailure(errorCode, errorMsg);
            }
        });
    }

}
