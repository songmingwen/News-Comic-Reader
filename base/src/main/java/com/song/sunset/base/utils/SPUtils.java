package com.song.sunset.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */

public class SPUtils {

    public static final String APP_USER_KEY = "app_user_key";
    public static final String APP_IMEI_KEY = "app_imei_key";
    public static final String APP_ANDROID_ID_KEY = "app_androidid_key";
    public static final String APP_NIGHT_MODE = "night_mode";
    public static final String APP_FIRST_INSTALL = "app_first_install";
    public static final String SP_APP_PLAY_POSITION = "normal_video_player_play_position";
    public static final String SP_NEURAL_NET_WORKS = "neural_net_works";
    public static final String SP_NEURAL_NET_WORKS_PREVIEW = "neural_net_works_preview";
    public static final int NO_SP_MODE = MODE_PRIVATE;

    public static String getStringByName(Context context, String key,
                                         String defaultValue) {
        return getStringByName(context, null, NO_SP_MODE, key, defaultValue);
    }

    public static String getStringByName(Context context, String spName, int spMode, String key, String defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, spName, spMode);
        return sp.getString(key, defaultValue);
    }

    public static int getIntByName(Context context, String key,
                                   int defaultValue) {
        return getIntByName(context, null, NO_SP_MODE, key, defaultValue);
    }

    public static int getIntByName(Context context, String spName, int spMode, String key, int defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, spName, spMode);
        return sp.getInt(key, defaultValue);
    }

    public static long getLongByName(Context context, String key, long defaultValue) {
        return getLongByName(context, null, NO_SP_MODE, key, defaultValue);

    }

    public static long getLongByName(Context context, String spName, int spMode, String key, long defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, spName, spMode);
        return sp.getLong(key, defaultValue);
    }

    public static boolean getBooleanByName(Context context, String key,
                                           boolean defaultValue) {
        return getBooleanByName(context, null, NO_SP_MODE, key, defaultValue);

    }

    public static boolean getBooleanByName(Context context, String spName, int spMode, String key, boolean defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, spName, spMode);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setStringByName(Context context, String key,
                                       String value) {
        setStringByName(context, null, NO_SP_MODE, key, value);
    }

    public static void setStringByName(Context context, String spName, int spMode, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context, spName, spMode);
        editor.putString(key, value);
        editor.apply();
    }

    public static void setBooleanByName(Context context, String key, Boolean value) {
        setBooleanByName(context, null, NO_SP_MODE, key, value);
    }

    public static void setBooleanByName(Context context, String spName, int spMode, String key, Boolean value) {
        SharedPreferences.Editor editor = getEditor(context, spName, spMode);
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setIntByName(Context context, String key, int value) {
        setIntByName(context, null, NO_SP_MODE, key, value);
    }

    public static void setIntByName(Context context, String spName, int spMode, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context, spName, spMode);
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setLongByName(Context context, String key, long value) {
        setLongByName(context, null, NO_SP_MODE, key, value);
    }

    public static void setLongByName(Context context, String spName, int spMode, String key, long value) {
        SharedPreferences.Editor editor = getEditor(context, spName, spMode);
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setFloatByName(Context context, String key, float value) {
        setFloatByName(context, null, NO_SP_MODE, key, value);
    }


    public static float getFloatByName(Context context, String spName, int spMode, String key, float value) {
        SharedPreferences sp = getSharedPreferences(context, spName, spMode);
        return sp.getFloat(key, value);
    }

    public static void setFloatByName(Context context, String spName, int spMode, String key, float value) {
        SharedPreferences.Editor editor = getEditor(context, spName, spMode);
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloatByName(Context context, String key, float value) {
        return getFloatByName(context, null, NO_SP_MODE, key, value);
    }

    public static void clearValues(Context context) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public static void clearValueByKey(Context context, String name) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(name);
        editor.apply();
    }

    private static SharedPreferences.Editor getEditor(Context context, String spName, int spMode) {
        SharedPreferences sp = getSharedPreferences(context, spName, spMode);
        return sp.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context, String spName, int spMode) {
        SharedPreferences sp;
        if (TextUtils.isEmpty(spName)) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            sp = context.getSharedPreferences(spName, spMode);
        }
        return sp;
    }
}
