package com.song.sunset.base.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public interface PageEntity<T> extends Serializable {
    int getOnePageCount();

    int getCurrentPage();

    boolean isHasMore();

    List<T> getData();
}
