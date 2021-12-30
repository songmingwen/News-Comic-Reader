package com.song.sunset.hook.hookdangerapi.type.def;

import android.content.Context;

import com.song.sunset.hook.BaseHook;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.hookdangerapi.type.gap.GapHelper;
import com.song.sunset.hook.record.RecordInterface;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Desc:    监听 PackageManager 相关 api 的调用
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/17 9:25
 */
public class HookPackageManager extends BaseHook {

    public HookPackageManager(RecordInterface record) {
        super(record);
    }

    @Override
    public void startObserve(final Context context) {
        super.startObserve(context);

        ClassLoader classLoader = context.getClassLoader();
        Class clazz = null;
        try {
            clazz = classLoader.loadClass("android.app.ApplicationPackageManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (clazz != null) {
            XposedHelpers.findAndHookMethod(clazz, "getInstalledPackages", int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            append(new RecordData("getInstalledPackages", getThreadStacktrace(), GapHelper.getGapTime(context, "getInstalledPackages")));
                        }
                    });
            XposedHelpers.findAndHookMethod(clazz, "getInstalledApplications", int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            append(new RecordData("getInstalledApplications(int)", getThreadStacktrace(), GapHelper.getGapTime(context, "getInstalledApplications")));
                        }
                    });
            XposedHelpers.findAndHookMethod(clazz, "getApplicationInfo", String.class, int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            append(new RecordData("getApplicationInfo(String,int)", getThreadStacktrace(), GapHelper.getGapTime(context, "getApplicationInfo")));
                        }
                    });
        }
    }
}
