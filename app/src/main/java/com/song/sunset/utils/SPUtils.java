package com.song.sunset.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */

public class SPUtils {

    public static final String APP_USER_KEY = "app_user_key";
    public static final String APP_IMEI_KEY = "app_imei_key";
    public static final String APP_ANDROID_ID_KEY = "app_androidid_key";

    public static String getStringByName(Context context, String name,
                                         String default_value) {

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(name, default_value);

    }

    public static int getIntByName(Context context, String name,
                                   int default_value) {

        return PreferenceManager.getDefaultSharedPreferences(context).getInt(
                name, default_value);

    }

    public static long getLongByName(Context context, String name,
                                     long default_value) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(
                name, default_value);

    }

    public static boolean getBooleanByName(Context context, String name,
                                           boolean default_value) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(name, default_value);

    }

    public static void setStringByName(Context context, String name,
                                       String value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static void setBooleanByName(Context context, String name,
                                        Boolean value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static void setIntByName(Context context, String name, int value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public static void setLongByName(Context context, String name, long value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, value);
        editor.apply();
    }

    public static void clearValues(Context context) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public static void clearValueByKey(Context context , String name){
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(name);
        editor.apply();
    }
}
