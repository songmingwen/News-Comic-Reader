package com.song.sunset.mvp.models;

import android.support.annotation.NonNull;

import com.song.sunset.beans.CollectionOnlineListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.mvp.views.ComicCollectionView;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitFactory;
import com.song.sunset.utils.rxjava.RxUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Song on 2017/12/22 0022.
 * E-mail: z53520@qq.com
 */

public class ComicCollectionModel {

    public void getNewestCollectedComic(final ComicCollectionView view) {
        Observable<BaseBean<CollectionOnlineListBean>> observable = RetrofitFactory.createApi(U17ComicApi.class, getCollectionMap()).queryComicCollectionListRDByObservable("");
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

    @NonNull
    public static Map<String, String> getCollectionMap() {
        Map<String, String> map = new HashMap<>();
        map.put("v", "3360100");
        map.put("key", "387df5b33fc7fe893a7ca573591a9d82ee5695909ca89b94bc237d734e13762664c4437ea3069d86847388c198390f44b7c0947136188de4aca46c4adfd7eaf9c0844103fd9f7b9f554531ff99da3430222d17ed61d61cfede2d27cb667b3173%3A%3A%3Au17");
        map.put("t", System.currentTimeMillis() + "");
        return map;
    }
}
