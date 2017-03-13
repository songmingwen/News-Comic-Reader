package com.song.sunset.presenter;

import com.song.sunset.impls.ComicListView;
import com.song.sunset.model.ComicListModel;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 * E-mail:z53520@qq.com
 */
public class ComicListPresenter {
    private ComicListView comicListView;
    private ComicListModel comicListModel;

    public ComicListPresenter(ComicListView comicListView) {
        this.comicListView = comicListView;
        comicListModel = new ComicListModel();
    }

    public void loadingMoreData(String argName, int argValue) {
        comicListModel.loadingMoreData(argName, argValue, comicListView);
    }

    public void refreshingData(String argName, int argValue) {
        comicListModel.refreshingData(argName, argValue, comicListView);
    }
}
