package com.song.sunset.beans;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
public class TV {
    public TV(String tvName, String tvUrl) {
        this.tvName = tvName;
        this.tvUrl = tvUrl;
    }

    private String tvName;
    private String tvUrl;

    public void setTvUrl(String tvUrl) {
        this.tvUrl = tvUrl;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvName() {
        return tvName;
    }

    public String getTvUrl() {
        return tvUrl;
    }
}
