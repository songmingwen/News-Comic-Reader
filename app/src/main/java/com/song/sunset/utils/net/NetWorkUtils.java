package com.song.sunset.utils.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.retrofit.Net;
import com.trello.rxlifecycle3.LifecycleTransformer;

import androidx.annotation.NonNull;
import io.reactivex.ObservableTransformer;
import retrofit2.Response;

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

    public static <T> T createService(@NonNull Class<T> service) {
        return Net.createService(service);
    }

    /**
     * 使用它会：
     * 线程切换
     */
    public static <T> SchedulerTransformer<T> getScheduler() {
        return new SchedulerTransformer<>();
    }

    /**
     * 使用它会：
     * 1. 线程切换
     * 2. Response 剥离
     */
    public static <T> SimplifyRequestTransformer<T> simplifyRequest() {
        return new SimplifyRequestTransformer<>();
    }

    /**
     * 使用它会：
     * 1. 线程切换
     * 2. 绑定生命周期
     * 3. Response 剥离
     */
    public static <T> SimplifyRequestTransformer<T> simplifyRequest(LifecycleTransformer<Response<T>> lifecycleTransformer) {
        return new SimplifyRequestTransformer<>(lifecycleTransformer);
    }

    /**
     * 如果某一个请求，无论网络挂掉还是 API 返回错误都执行相同的错误处理逻辑
     * 那么就可以使用此方法简化操作, 统一在 onError 里处理
     * 如果需要执行不同的逻辑，那么不要使用此方法
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<Response<T>, Response<T>> throwAPIError() {
        return upper -> upper.doOnNext(response -> {
            if (!response.isSuccessful()) throw new RetrofitAPIError(response);
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        return getNetworkState(context) != NETWORK_NONE;
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
