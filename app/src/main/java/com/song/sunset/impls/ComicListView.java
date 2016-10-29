package com.song.sunset.impls;

import com.song.sunset.beans.ComicListBean;

import java.util.List;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 */
public interface ComicListView {

    void hideRefreshLayout();

    void showLoadingMoreProgress();

    void hideLoadingMoreProgress();

    void showContent(List<ComicListBean.ComicsBean> list, boolean isRefresh);

    void showLoading();

    void showError();
}
