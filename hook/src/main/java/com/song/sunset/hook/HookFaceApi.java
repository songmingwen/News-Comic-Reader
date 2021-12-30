package com.song.sunset.hook;

import android.app.Application;

/**
 * Desc:    hook dangerapi 统一对外方法
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/20 16:36
 */
public class HookFaceApi {

    /**
     * 初始化 hook
     */
    public static void initHook(Application application) {
        //非插件化入口
        HookManager.init(application);

        //插件入口，后期有需求可以放开
        //HookManager.loadApk(application);
    }

}