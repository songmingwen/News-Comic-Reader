package com.song.sunset.hook;

import android.app.Application;
import android.util.Log;

import com.song.sunset.hook.hookdangerapi.HookDangerApiClient;
import com.song.sunset.hook.hookdangerapi.HookEventClient;
import com.song.sunset.hook.hookdangerapi.HookGapDangerApiClient;
import com.song.sunset.hook.hooknet.HookNetClient;

/**
 * Desc:    开始各种监听
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/28 15:06
 */
public class HookManager {

    private static final String TAG = "song-HookStarter";

    public static void init(Application context) {
        Log.i(TAG, "init hook");
        //开始对应点击事件监控
        HookEventClient.startObserve(context);
        //开始隐私页面危险 api 监控
        HookDangerApiClient.getInstance().startObserve(context);
        //开始隐私页面危险网络请求监控
        HookNetClient.getInstance().startObserve(context);
        //开始危险 api 间隔时间请求监控
        HookGapDangerApiClient.getInstance().startObserve(context);
    }

    /***
     * 插件入口
     * @param context
     */
    public static void loadApk(Application context) {
        ApkLoader.init(context);
    }
}
