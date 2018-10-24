package com.song.sunset.utils;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
public class HuaweiPreinstallHandler implements PreinstallHandler {

    public static final String HUAWEI_PREINSTALL_KEY = "ro.huawei.channel.zhihu";//华为预装约定的key值不得超过30字节，value目前是huawei_preinstall。value要小于60字节

    private PreinstallHandler mNextPreinstallHandler;

    @Override
    public String getPreinstallInfo() {
        String info = getHuaweiPreinstallSystemInfo();
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

    private String getHuaweiPreinstallSystemInfo() {
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getDeclaredMethod("get", String.class, String.class);
            return (String) method.invoke(clazz, HUAWEI_PREINSTALL_KEY, "");
        } catch (Exception e) {
        }
        return "";
    }
}
