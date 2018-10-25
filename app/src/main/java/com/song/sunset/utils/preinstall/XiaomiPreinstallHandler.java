package com.song.sunset.utils.preinstall;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
public class XiaomiPreinstallHandler implements PreinstallHandler {

    private PreinstallHandler mNextPreinstallHandler;

    @Override
    public String getPreinstallInfo() {
        String info = getXiaomiPresinstallSystemInfo();
        if (!TextUtils.isEmpty(info)) {
            return info;
        } else {
            if (mNextPreinstallHandler != null) {
                return mNextPreinstallHandler.getPreinstallInfo();
            } else {
                return "";
            }
        }
    }

    @Override
    public void setNextHandler(PreinstallHandler preinstallHandler) {
        mNextPreinstallHandler = preinstallHandler;
    }

    private String getXiaomiPresinstallSystemInfo() {
        try {
            String packageName = "com.zhihu.android";
            Class<?> miui = Class.forName("miui.os.MiuiInit");
            Method method = miui.getMethod("getMiuiChannelPath", String.class);
            return (String) method.invoke(null, packageName);
        } catch (Exception e) {
        }
        return "";
    }
}
