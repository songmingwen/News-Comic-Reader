package com.song.sunset.view;

import com.song.core.base.CoreBaseView;
import com.song.sunset.beans.ComicDetailBean;

/**
 * Created by Song on 2016/12/8.
 * E-mail:z53520@qq.com
 */
public interface ComicDetailView extends CoreBaseView {
    void setData(ComicDetailBean comicDetailBean);

    void showCollected(boolean shouldShow,boolean shouldShowMsg);
}
