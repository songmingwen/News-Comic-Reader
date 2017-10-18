package com.song.sunset.mvp.presenters;

import com.song.sunset.beans.ChapterListBean;
import com.song.sunset.interfaces.ComicReadView;
import com.song.sunset.mvp.models.ComicReadModel;

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
