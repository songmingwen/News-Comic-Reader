package com.song.sunset.hook;

import android.app.Application;

import com.song.sunset.hook.config.Config;
import com.song.sunset.hook.config.ConfigManager;

/**
 * Desc:    hook dangerapi 统一对外方法
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/20 16:36
 */
public class HookApi {

    /**
     * 初始化 hook
     */
    public static void initHook(Application application) {
        //非插件化入口
        HookManager.init(application);

        //插件入口，后期有需求可以放开
        //HookManager.loadApk(application);
    }

    public static void setConfig(Config config) {
        ConfigManager.getInstance().setConfig(config);
    }

}