package com.song.sunset.utils.preinstall;

import android.text.TextUtils;

import com.song.sunset.utils.preinstall.PreinstallHandler;

import java.lang.reflect.Method;

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
public class VivoPreinstallHandler implements PreinstallHandler {

    private static final String VIVO_PREINSTALL_KEY = "ro.preinstall.path";

    private PreinstallHandler mNextPreinstallHandler;

    @Override
    public String getPreinstallInfo() {
        String info = getVivoPreinstallSystemInfo();
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

    public String getVivoPreinstallSystemInfo() {
        String value = "";
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getDeclaredMethod("get", String.class, String.class);
            value = (String) method.invoke(clazz, VIVO_PREINSTALL_KEY, "");
        } catch (Exception e) {
        } finally {
            return value;
        }
    }
}
