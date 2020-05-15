package com.song.sunset;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;
import io.flutter.view.FlutterMain;
import io.reactivex.Observable;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.song.core.FrescoInitializer;
import com.song.sunset.activitys.temp.GlobalFlowActivity;
import com.song.sunset.enums.Weeks;
import com.song.sunset.services.managers.BinderPool;
import com.song.sunset.services.managers.MusicGetterManager;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.services.managers.MessengerManager;
import com.song.sunset.services.managers.PushManager;
import com.song.sunset.utils.SPUtils;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.lifecycle.BaseActivityLifecycle;
import com.song.sunset.utils.lifecycle.LifecycleManager;
import com.song.sunset.utils.net.RxNetWork;
import com.song.sunset.widget.GlobalFlowView;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class SunsetApplication extends MultiDexApplication {

    private boolean isMainProcess;

    @Override
    public void onCreate() {
        super.onCreate();
        initProcess();
        if (isMainProcess) {
            initInMainProcess();
        }
    }

    private void initInMainProcess() {
        AppConfig.setApp(this);

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        if (SPUtils.getBooleanByName(this, SPUtils.APP_FIRST_INSTALL, true)) {
            SPUtils.setBooleanByName(this, SPUtils.APP_FIRST_INSTALL, true);
        }

        GreenDaoUtil.initGreenDao();

        FrescoInitializer.getDefaultInstance().initialize(this);

        String dir = MMKV.initialize(this);
        Log.i("initInMainProcess: ", dir);

        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode("boolean", true);
        mmkv.encode("string", "string");
        mmkv.encode("int", 7777777);
        Log.i("initInMainProcess: ", mmkv.decodeString("string") + "," + mmkv.decodeBool("boolean") + "," + mmkv.decodeInt("int"));

//        CrashHandler.getInstance().init();
        PushManager.getInstance().init(this);
        BinderPool.getInstance();
        MessengerManager.getInstance().init(this);
        MusicGetterManager.getInstance().init(this);

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

        //生命周期监听
        addLifecycleListener();

        FlutterMain.startInitialization(this);
    }

    private void addLifecycleListener() {
        LifecycleManager lifecycleManager = new LifecycleManager(this);
        lifecycleManager.addLifeCycle("测试", new BaseActivityLifecycle() {

            @Override
            public void onActivityResumed(Activity activity) {
                super.onActivityResumed(activity);
                MMKV store = MMKV.defaultMMKV();
                boolean show = store.getBoolean("show_global_flow", true);
                if (show) {
                    GlobalFlowActivity.Companion.showGlobalFlowView(activity);
                } else {
                    GlobalFlowActivity.Companion.hideView(activity);
                }
            }
        });
        lifecycleManager.init();
    }

    private void initProcess() {
        int nProcessId = Process.myPid();
        ActivityManager actvityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo procInfo : procInfos) {
            if (nProcessId == procInfo.pid) {
                if (!TextUtils.isEmpty(procInfo.processName)) {
                    if (getPackageName().equals(procInfo.processName)) {
                        // check if it is main process.
                        isMainProcess = true;
                    }
                }
            }
        }
    }

}
