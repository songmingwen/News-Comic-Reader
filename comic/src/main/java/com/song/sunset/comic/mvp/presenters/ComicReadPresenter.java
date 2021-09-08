package com.song.sunset.comic.mvp.presenters;

import com.song.sunset.comic.bean.ChapterListBean;
import com.song.sunset.comic.mvp.models.ComicReadModel;
import com.song.sunset.comic.mvp.views.ComicReadView;

import java.util.ArrayList;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadPresenter {
    private ComicReadView mComicReadView;
    private ComicReadModel mComicReadModel;

    public ComicReadPresenter(ComicReadView comicReadView) {
        this.mComicReadView = comicReadView;
        this.mComicReadModel = new ComicReadModel();
    }

    public void setChapterList(ArrayList<ChapterListBean> list, int firstPosition) {
        if (mComicReadModel == null) return;
        mComicReadModel.setChapterList(list,mComicReadView);
        firstLoad(firstPosition);
    }

    public void firstLoad(int firstPosition) {
        if (mComicReadModel == null) return;
        mComicReadModel.firstLoad(firstPosition);
    }

    public void loadTop() {
        if (mComicReadModel == null) return;
        mComicReadModel.loadTop();
    }

    public void loadBottom() {
        if (mComicReadModel == null) return;
        mComicReadModel.loadBottom();
    }
}
