package com.song.sunset.phoenix.bean;

import java.io.Serializable;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/3/10 15:24
 */
public class WidgetItem implements Serializable {

    public String title;

    public String className;

    public String image;

    public String jumpUrl;

    public byte[] bitmap;

    @Override
    public String toString() {
        return "WidgetItem{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
