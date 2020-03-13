package com.song.sunset.utils.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.song.sunset.utils.AppConfig;

import androidx.annotation.NonNull;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class NetWorkUtils {

    //没有网络连接
    public static final int NETWORK_NONE = 0;
    //wifi连接
    public static final int NETWORK_WIFI = 1;
    //手机网络数据连接类型
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_3G = 3;
    public static final int NETWORK_4G = 4;
    public static final int NETWORK_MOBILE = 5;
    public static final int NETWORK_ETHERNET = 6;

    public static boolean isNetWorking() {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) AppConfig.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        result = netinfo != null && netinfo.isConnected();
        return result;
    }

    /**
     * 获取当前网络连接类型
     */
    @SuppressLint("MissingPermission")
    public static int getNetworkState(@NonNull Context context) {

        if (context == null) {
            return NETWORK_NONE;
        }

        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //如果当前没有网络
        if (null == connManager) {
            return NETWORK_NONE;
        }

        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }

        // 判断是不是连接的是不是wifi
        if (isNetworkTypeConnected(connManager, ConnectivityManager.TYPE_WIFI)) {
            return NETWORK_WIFI;
        }

        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_2G;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if ("TD-SCDMA".equalsIgnoreCase(strSubTypeName) || "WCDMA".equalsIgnoreCase(strSubTypeName)
                                    || "CDMA2000".equalsIgnoreCase(strSubTypeName)) {
                                return NETWORK_3G;
                            } else {
                                return NETWORK_MOBILE;
                            }
                    }
                }
            }
        }

        if (activeNetInfo.isConnected() && activeNetInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
            return NETWORK_ETHERNET;
        }

        return NETWORK_NONE;
    }

    /**
     * 判断某个类型的网络是否连接了
     */
    @SuppressLint("MissingPermission")
    private static boolean isNetworkTypeConnected(ConnectivityManager connManager, int networkType) {
        NetworkInfo networkInfo = connManager.getNetworkInfo(networkType);
        if (networkInfo != null) {
            NetworkInfo.State state = networkInfo.getState();
            return state != null && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING);
        }
        return false;
    }
}
