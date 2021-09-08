package com.song.sunset.comic.mvp.views;

import com.song.sunset.comic.bean.ComicDetailBean;
import com.song.sunset.comic.mvp.CoreBaseView;

/**
 * Created by Song on 2016/12/8.
 * E-mail:z53520@qq.com
 */
public interface ComicDetailView extends CoreBaseView {
    void setData(ComicDetailBean comicDetailBean);

    void showCollected(boolean shouldShow,boolean shouldShowMsg);
}
