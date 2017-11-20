package com.song.sunset.utils;

import android.content.Context;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class AppConfig {

    private static Context context;

    public static final int REFRESH_CLOSE_TIME = 500;

    public static void setApp(Context outContext) {
        context = outContext;
    }

    public static Context getApp() {
        return context;
    }
}
