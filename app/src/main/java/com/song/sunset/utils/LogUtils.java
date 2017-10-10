package com.song.sunset.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by Song on 2017/10/10 0010.
 * E-mail: z53520@qq.com
 */

public class LogUtils {

    public static boolean APP_DEBUG = false; // 是否是debug模式

    public static void init(Context context) {
        APP_DEBUG = isApkDebugable(context);
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     */
    private static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
