package com.song.sunset.impls;

import com.song.sunset.beans.ComicReadImageListBean;

import java.util.List;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public interface ComicReadView {
    void loadFirstEnd(boolean success, List<ComicReadImageListBean> list);

    void loadTopEnd(boolean success, List<ComicReadImageListBean> list);

    void loadBottomEnd(boolean success, List<ComicReadImageListBean> list);
}
