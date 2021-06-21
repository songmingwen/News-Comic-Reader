package com.song.sunset.beans.zhihu;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @author songmingwen
 * @description
 * @since 2019/6/24
 */
@Keep
public class InterestList {

    private String title;

    private List<TagsList> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TagsList> getData() {
        return data;
    }

    public void setData(List<TagsList> data) {
        this.data = data;
    }
}
