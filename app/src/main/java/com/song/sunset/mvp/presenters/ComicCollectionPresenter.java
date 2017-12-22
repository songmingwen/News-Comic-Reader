package com.song.sunset.mvp.presenters;

import com.song.core.base.CoreBasePresenter;
import com.song.sunset.mvp.models.ComicCollectionModel;
import com.song.sunset.mvp.views.ComicCollectionView;

/**
 * Created by Song on 2017/12/22 0022.
 * E-mail: z53520@qq.com
 */

public class ComicCollectionPresenter extends CoreBasePresenter<ComicCollectionModel, ComicCollectionView> {

    @Override
    public void onStart() {

    }

    public void getNewestCollectedComic() {
        if (mModel == null || mView == null) return;
        mModel.getNewestCollectedComic(mView);
    }
}
