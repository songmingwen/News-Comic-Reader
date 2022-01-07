package com.song.sunset.hook.config;

import com.song.sunset.hook.bean.ApiData;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/1/6 17:29
 */
public abstract class BaseConfig implements Config {
    protected ArrayList<String> list = null;

    protected BaseConfig() {
        addConfig();
    }

    protected abstract void addConfig();

    protected void addApi(@ApiData.ApiName String apiName) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(apiName);
    }

    public List<String> getConfig() {
        return list;
    }

}
