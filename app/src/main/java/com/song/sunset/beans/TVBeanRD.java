package com.song.sunset.beans;

import java.util.List;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
public class TVBeanRD {

    private List<TV> tvList;

    public TVBeanRD(List<TV> tvList) {
        this.tvList = tvList;
    }

    public void setTvList(List<TV> tvList) {
        this.tvList = tvList;
    }

    public List<TV> getTvList() {
        return tvList;
    }
}
