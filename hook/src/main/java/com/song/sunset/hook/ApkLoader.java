package com.song.sunset.hook;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.song.sunset.hook.utils.ClassUtil;
import com.song.sunset.hook.utils.MyLog;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Desc:    Load插件
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/28 14:28
 */
public class ApkLoader {
    private static final String TAG = "song-HookLoader";
    private static final String APKHOOK_PACKAGE_NAME = "com.mgtv.helper";
    private static Application mApplication;

    public static void init(Application application) {
        mApplication = application;
        findAndLoadDex(APKHOOK_PACKAGE_NAME);
    }

    private static void findAndLoadDex(String packageName) {
        try {
            Class<?> aClassActivityThread = Class.forName("android.app.ActivityThread");
            Object currentActivityThread = ClassUtil.invokeStaticMethod(aClassActivityThread, "currentActivityThread");
            Context mSystemContext = (Context) ClassUtil.invokeMethod(currentActivityThread, "getSystemContext");
            PackageManager packageManager = mSystemContext.getPackageManager();
            List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
            for (PackageInfo installedPackage : installedPackages) {
                if (TextUtils.equals(installedPackage.packageName, packageName)) {
                    loadApk(mSystemContext, installedPackage.applicationInfo);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "dex加载失败", e);
        }
    }

    private static void loadApk(Context mSystemContext, ApplicationInfo applicationInfo) {
        try {
            String librarySearchPath = "";
            if (isART64(mSystemContext)) {
                librarySearchPath = applicationInfo.sourceDir + "!/lib/arm64-v8a";
            } else {
                librarySearchPath = applicationInfo.sourceDir + "!/lib/armeabi-v7a";
            }
            File[] nativeLibraryDirFiles = new File(applicationInfo.nativeLibraryDir).listFiles();
            if (nativeLibraryDirFiles != null && nativeLibraryDirFiles.length > 0) {
                librarySearchPath = applicationInfo.nativeLibraryDir;
            }
            MyLog.log("librarySearchPath: " + librarySearchPath);
            DexClassLoader dexClassLoader = new DexClassLoader(applicationInfo.sourceDir, mSystemContext.getApplicationInfo().dataDir, librarySearchPath, mSystemContext.getClassLoader());
            Class hookBridgeClass = dexClassLoader.loadClass("com.mgtv.helper.hook.XposedHander");
            if (hookBridgeClass != null) {
                if (mApplication != null) {
                    hookBridgeClass.getDeclaredMethod("init", Context.class).invoke(null, mApplication);
                } else {
                    hookBridgeClass.getDeclaredMethod("init", Context.class).invoke(null, mSystemContext);
                }
                Log.d(TAG, "加载扩展dex成功");
            }
        } catch (Exception e) {
            MyLog.log(e);
        }

    }

    private static boolean isART64(Context mSystemContext) {
        final String fileName = "art";
        try {
            ClassLoader classLoader = mSystemContext.getClassLoader();
            Class<?> cls = ClassLoader.class;
            Method method = cls.getDeclaredMethod("findLibrary", String.class);
            Object object = method.invoke(classLoader, fileName);
            if (object != null) {
                return ((String) object).contains("lib64");
            }
        } catch (Exception e) {
            MyLog.log(e);
        }
        return false;
    }

    public static void start() {

    }

}
