package com.song.sunset.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ViewUtil {

    public static WindowManager getWindowManager() {
        return (WindowManager) AppConfig.getApp().getSystemService(Context.WINDOW_SERVICE);
    }

    public static int getScreenWidth() {
        return getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeigth() {
        return getWindowManager().getDefaultDisplay().getHeight();
    }

    public static int dip2px(float dpValue) {
        final float scale = AppConfig.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = AppConfig.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
