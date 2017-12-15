package com.song.sunset.interfaces;

import com.song.sunset.beans.ComicReadImageListBean;

import java.util.List;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public interface ComicReadView {
    void loadFirstPage(boolean success, List<ComicReadImageListBean> list);

    void loadPreviousPage(boolean success, List<ComicReadImageListBean> list);

    void loadNextPage(boolean success, List<ComicReadImageListBean> list);
}
