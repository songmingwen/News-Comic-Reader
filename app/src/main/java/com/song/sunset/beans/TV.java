package com.song.sunset.beans;

import androidx.annotation.Keep;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
@Keep
public class TV {
    public TV(String tvName, String tvUrl) {
        this.tvName = tvName;
        this.tvUrl = tvUrl;
    }

    public
    String tvName;
    public
    String tvUrl;
    public
    boolean selected;
}
