package com.song.sunset.hook.hookdangerapi.type.def;

import android.content.Context;

import com.song.sunset.hook.BaseHook;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.config.ConfigManager;
import com.song.sunset.hook.hookdangerapi.type.gap.GapHelper;
import com.song.sunset.hook.record.RecordInterface;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.song.sunset.hook.bean.ApiData.*;

/**
 * Desc:    监听 TelephonyManager 相关 api 的调用
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 9:24
 */
public class HookTelephonyManager extends BaseHook {

    public HookTelephonyManager(RecordInterface record) {
        super(record);
    }

    @Override
    public void startObserve(final Context context) {
        super.startObserve(context);
        ClassLoader classLoader = context.getClassLoader();
        Class clazzTele = null;
        try {
            clazzTele = classLoader.loadClass("android.telephony.TelephonyManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (clazzTele != null) {

            if (ConfigManager.getInstance().apiInConfig(IMEI)) {
                try {
                    XposedHelpers.findAndHookMethod(clazzTele, "getImei",
                            new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) {
                                    append(new RecordData("getImei", getThreadStacktrace(context), GapHelper.getGapTime(context, "getImei")));
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ConfigManager.getInstance().apiInConfig(MEID)) {
                try {
                    XposedHelpers.findAndHookMethod(clazzTele, "getMeid",
                            new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) {
                                    append(new RecordData("getMeid", getThreadStacktrace(context), GapHelper.getGapTime(context, "getMeid")));
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ConfigManager.getInstance().apiInConfig(DEVICE_ID)) {
                try {
                    XposedHelpers.findAndHookMethod(clazzTele, "getDeviceId",
                            new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) {
                                    append(new RecordData("getDeviceId", getThreadStacktrace(context), GapHelper.getGapTime(context, "getDeviceId")));
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ConfigManager.getInstance().apiInConfig(SUBSCRIBER_ID)) {
                try {
                    XposedHelpers.findAndHookMethod(clazzTele, "getSubscriberId",
                            new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) {
                                    append(new RecordData("getSubscriberId", getThreadStacktrace(context), GapHelper.getGapTime(context, "getSubscriberId")));
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
