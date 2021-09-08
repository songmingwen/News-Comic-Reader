package com.song.sunset.comic.mvp.presenters;

import com.song.sunset.comic.mvp.models.ComicCollectionModel;
import com.song.sunset.comic.mvp.views.ComicCollectionView;

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
