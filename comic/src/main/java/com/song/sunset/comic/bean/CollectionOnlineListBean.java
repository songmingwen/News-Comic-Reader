package com.song.sunset.comic.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * Created by Song on 2017/12/20 0020.
 * E-mail: z53520@qq.com
 */
@Keep
public class CollectionOnlineListBean {
    private List<ComicCollectionBean> favList;

    public List<ComicCollectionBean> getFavList() {
        return favList;
    }

    public void setFavList(List<ComicCollectionBean> favList) {
        this.favList = favList;
    }

}
