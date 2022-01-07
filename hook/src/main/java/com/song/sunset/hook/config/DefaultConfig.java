package com.song.sunset.hook.config;

import com.song.sunset.hook.bean.ApiData;

/**
 * Desc:    敏感 api 监控默认配置
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/1/6 16:23
 */
public class DefaultConfig extends BaseConfig {

    @Override
    public void addConfig() {
        addApi(ApiData.OAID);
        addApi(ApiData.IMEI);
        addApi(ApiData.MEID);
        addApi(ApiData.DEVICE_ID);
        addApi(ApiData.SUBSCRIBER_ID);
        addApi(ApiData.INSTALLED_PACKAGES);
        addApi(ApiData.INSTALLED_APPLICATION);
        addApi(ApiData.APPLICATION_INFO);
        addApi(ApiData.SOCKET_CONNECT);
    }
}
