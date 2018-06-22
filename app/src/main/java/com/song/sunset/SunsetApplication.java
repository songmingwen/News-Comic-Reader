package com.song.sunset;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.song.core.FrescoInitializer;
import com.song.sunset.services.managers.BinderPool;
import com.song.sunset.services.managers.MusicGetterManager;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.services.managers.MessengerManager;
import com.song.sunset.services.managers.PushManager;
import com.song.sunset.utils.SPUtils;
import com.song.sunset.utils.AppConfig;

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

        if (SPUtils.getBooleanByName(this, SPUtils.APP_FIRST_INSTALL, true)) {
            SPUtils.setBooleanByName(this, SPUtils.APP_FIRST_INSTALL, true);
        }

        GreenDaoUtil.initGreenDao();

        FrescoInitializer.getDefaultInstance().initialize(this);


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
