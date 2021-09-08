package com.song.sunset.interfaces;

import com.song.sunset.comic.bean.ComicsBean;

import java.util.List;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 */
public interface ComicListView {

    void hideRefreshLayout();

    void showLoadingMoreProgress();

    void hideLoadingMoreProgress();

    void showContent(List<ComicsBean> list, boolean isRefresh);

    void showLoading();

    void showError();
}
