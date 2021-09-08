package com.song.sunset.comic.mvp.views;

import com.song.sunset.comic.bean.CollectionOnlineListBean;

/**
 * Created by Song on 2017/12/22 0022.
 * E-mail: z53520@qq.com
 */

public interface ComicCollectionView {
    void onSuccess(CollectionOnlineListBean collectionOnlineListBean);

    void onFailure(int errorCode, String errorMsg);
}
