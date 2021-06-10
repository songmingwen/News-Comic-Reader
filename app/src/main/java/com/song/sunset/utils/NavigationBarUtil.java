package com.song.sunset.utils;

import android.content.res.Resources;

import com.song.sunset.base.AppConfig;

/**
 * @author songmingwen
 * @description
 * @since 2020/3/24
 */
public class NavigationBarUtil {

    public static int getNavigationBarHeight() {
        Resources resources = AppConfig.getApp().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

}
