package com.song.sunset.hook.bean;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Desc:    api 信息
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/1/6 16:41
 */
public class ApiData {
    //广告联盟 oaid
    public static final String OAID = "oaid";

    /*** ------ TelephonyManager 系统方法，注释省略 -------*/
    public static final String IMEI = "imei";
    public static final String MEID = "meid";
    public static final String DEVICE_ID = "deviceId";
    public static final String SUBSCRIBER_ID = "subscriberId";

    /*** ------ PackageManager 系统方法，注释省略-------*/
    public static final String INSTALLED_PACKAGES = "getInstalledPackages";
    public static final String INSTALLED_APPLICATION = "getInstalledApplications";
    public static final String APPLICATION_INFO = "getApplicationInfo";

    //网络请求
    public static final String SOCKET_CONNECT = "socket_connect";

    @StringDef({
            OAID,
            IMEI,
            MEID,
            DEVICE_ID,
            SUBSCRIBER_ID,
            INSTALLED_PACKAGES,
            INSTALLED_APPLICATION,
            APPLICATION_INFO,
            SOCKET_CONNECT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApiName {
    }
}
