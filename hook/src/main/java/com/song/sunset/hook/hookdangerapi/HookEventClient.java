package com.song.sunset.hook.hookdangerapi;

import android.content.Context;
import android.util.Log;

import com.song.sunset.hook.hooknet.HookNetClient;
import com.song.sunset.hook.ui.activity.HookResultActivity;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Desc:    监听用户点击同意隐私协议事件等各种事件
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/28 15:12
 */
public class HookEventClient {

    private static final String TAG = "song-HookEventClient";

    public static void startObserve(Context context) {
        Log.i(TAG, "startObserve");
        observeShowResultClick(context);
    }

    /**
     * 监听展示结果按钮
     */
    private static void observeShowResultClick(final Context context) {
        ClassLoader classLoader = context.getClassLoader();
        Class clazz = null;
        try {
            clazz = classLoader.loadClass("com.song.sunset.activitys.temp.FunctionListActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (clazz != null) {
            XposedHelpers.findAndHookMethod(clazz, "showResult",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            Log.i(TAG, "showResult 点击");

                            HookDangerApiClient.getInstance().stopObserve();
                            HookNetClient.getInstance().stopObserve();
                            HookGapDangerApiClient.getInstance().stopObserve();

                            HookResultActivity.startActivity(context);
                        }
                    });
        }
    }
}
