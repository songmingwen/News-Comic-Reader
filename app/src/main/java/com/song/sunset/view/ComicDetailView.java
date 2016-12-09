package com.song.sunset.view;

import com.song.core.base.CoreBaseView;
import com.song.sunset.beans.ComicDetailBean;

/**
 * Created by songmw3 on 2016/12/8.
 */
public interface ComicDetailView extends CoreBaseView {
    void setData(ComicDetailBean comicDetailBean);

    void showCollected(boolean shouldShow,boolean shouldShowMsg);
}
