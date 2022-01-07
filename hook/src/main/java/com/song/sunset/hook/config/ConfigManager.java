package com.song.sunset.hook.config;

import android.text.TextUtils;

import java.util.List;

/**
 * Desc:    config 管理类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/1/6 16:47
 */
public class ConfigManager {

    private static final ConfigManager configManager = new ConfigManager();

    private Config mConfig;

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        return configManager;
    }

    public void setConfig(Config config) {
        mConfig = config;
    }

    public boolean apiInConfig(String apiName) {
        if (mConfig == null) {
            mConfig = new DefaultConfig();
        }
        List<String> list = mConfig.getConfig();
        if (list != null && !list.isEmpty()) {
            for (String item : list) {
                if (TextUtils.equals(apiName, item)) {
                    return true;
                }
            }
        }
        return false;
    }

}
