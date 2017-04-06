package com.song.sunset.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */

public class DeviceUtils {

    public static String getAuthenticationID(Context ctx) {
        //如果是阿里云系统,使用系统UUID做设备唯一标识
        if (isYunOs()) {
            if (!"0".equals(getCloudUUID())) {
                return getCloudUUID();
            }
        }
        if (null == ctx) {
            return "0";
        }
        String authID = SPUtils.getStringByName(ctx, SPUtils.APP_USER_KEY, "");

        if (!TextUtils.isEmpty(authID)) {
            return authID;
        }
        authID = getSimIMEI(ctx);
        if (TextUtils.isEmpty(authID)) {
            authID = getAndroidID(ctx);
        }
        SPUtils.setStringByName(ctx, SPUtils.APP_USER_KEY, authID);
        return TextUtils.isEmpty(authID) ? "0" : authID;
    }

    private static String getCloudUUID() {
        String uuid;
        if (!"false".equals(//普通版yunos
                (uuid = getSystemProperty("ro.aliyun.clouduuid", "false")))) {
            return uuid;
        } else if (!"false".equals(//魅族版yunos
                (uuid = getSystemProperty("ro.sys.aliyun.clouduuid", "false")))) {
            return uuid;
        }
        return "0";
    }

    //因为UUID需要首次通过网络生成， 因此在刷机开机/工厂还原开机/首次联网时间段内会暂时取不到UUID值的情况
    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
            Method m = SystemProperties.getMethod("get", String.class, String.class);
            String result = (String) m.invoke(null, key, defaultValue);
            return result;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 获取IMEI，算法如下：
     * 1。 通过TelephonyManager.getDeviceId()获取唯一标识，若果唯一标示为空或长度小于8（如：#， 0， 1， null, Unknown等）
     * ，或为一些已知的会重复的号码则继续往下，否则返回唯一号。特殊号码包括：
     * 00000000
     * 00000000000000
     * 000000000000000
     * 111111111111111
     * 004999010640000
     * 352751019523267
     * 353867052181927
     * 358673013795895
     * 353163056681595
     * 352273017386340
     * 353627055437761
     * 351869058577423
     * <p>
     * 2. 通过WifiManager.getConnectionInfo().getMacAddress()取MAC地址作为唯一标识，若取得的是一些已知的会重复地址
     * 则继续往下，否则返回MAC作为唯一号。特殊地址包括：00:00:00:00:00:00
     * 3. 通过生成随机UUID作为唯一标识
     *
     * @param ctx
     * @return
     */
    public static String getSimIMEI(Context ctx) {
        String imei = SPUtils.getStringByName(ctx, SPUtils.APP_IMEI_KEY, "");
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) ctx
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (null != telephonyManager) {
                imei = telephonyManager.getDeviceId();
            }
            // get android id in case getDeviceId failed or get invalid id
            if (TextUtils.isEmpty(imei)
                    || imei.length() < 8
                    || isStringWithSameChar(imei)
                    || "004999010640000".equals(imei)
                    || "352751019523267".equals(imei)
                    || "353867052181927".equals(imei)
                    || "358673013795895".equals(imei)
                    || "353163056681595".equals(imei)
                    || "352273017386340".equals(imei)
                    || "353627055437761".equals(imei)
                    || "351869058577423".equals(imei)
                    ) {
                imei = "";
            }
        } catch (Exception e) {
        }
        SPUtils.setStringByName(ctx, SPUtils.APP_IMEI_KEY, imei);
        return imei;
    }

    private static boolean isYunOs() {
        if ((System.getProperty("java.vm.name") != null
                && System.getProperty("java.vm.name").toLowerCase().contains("lemur"))
                || (null != System.getProperty("ro.yunos.version"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取 Android ID
     *
     * @param ctx
     * @return Android ID
     */
    public static String getAndroidID(Context ctx) {
        String androidID = SPUtils.getStringByName(ctx, SPUtils.APP_ANDROID_ID_KEY, "");
        if (!TextUtils.isEmpty(androidID)) {
            return androidID;
        }
        androidID = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        SPUtils.setStringByName(ctx, SPUtils.APP_ANDROID_ID_KEY, androidID);
        return androidID;
    }

    /**
     * 判断字符串是否由相同的字符组成，比如“000000000000000”
     *
     * @param str
     * @return
     */
    public static boolean isStringWithSameChar(String str) {
        if (null == str || str.length() < 2) {
            return true;
        }
        return str.replace(str.substring(0, 1), "").length() == 0;
    }
}
