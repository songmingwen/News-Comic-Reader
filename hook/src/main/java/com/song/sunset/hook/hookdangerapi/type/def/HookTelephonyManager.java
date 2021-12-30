package com.song.sunset.hook.hookdangerapi.type.def;

import android.content.Context;

import com.song.sunset.hook.BaseHook;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.hookdangerapi.type.gap.GapHelper;
import com.song.sunset.hook.record.RecordInterface;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

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
        Class clazz = null;
        try {
            clazz = classLoader.loadClass("android.telephony.TelephonyManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (clazz != null) {
            XposedHelpers.findAndHookMethod(clazz, "getImei",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            append(new RecordData("getImei", getThreadStacktrace(), GapHelper.getGapTime(context, "getImei")));
                        }
                    });
            XposedHelpers.findAndHookMethod(clazz, "getMeid",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            append(new RecordData("getMeid", getThreadStacktrace(), GapHelper.getGapTime(context, "getMeid")));
                        }
                    });
            XposedHelpers.findAndHookMethod(clazz, "getDeviceId",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            append(new RecordData("getDeviceId", getThreadStacktrace(), GapHelper.getGapTime(context, "getDeviceId")));
                        }
                    });
            XposedHelpers.findAndHookMethod(clazz, "getSubscriberId",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            append(new RecordData("getSubscriberId", getThreadStacktrace(), GapHelper.getGapTime(context, "getSubscriberId")));
                        }
                    });
        }
    }
}
