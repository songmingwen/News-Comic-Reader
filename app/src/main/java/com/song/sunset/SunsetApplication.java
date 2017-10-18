package com.song.sunset;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.song.sunset.activitys.MainActivity;
import com.song.sunset.services.managers.BinderPool;
import com.song.sunset.utils.CrashHandler;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.services.managers.MessengerManager;
import com.song.sunset.services.managers.PushManager;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.utils.AppConfig;
import com.squareup.leakcanary.LeakCanary;

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

        GreenDaoUtil.initGreenDao();

        initLoadingAndRetryLayout();

//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(this, OkHttpClient.INSTANCE.getOkHttpClient())
//                .build();

        Fresco.initialize(this, FrescoUtil.getDefaultImagePipelineConfig(this));
        CrashHandler.getInstance().init(this);
        PushManager.getInstance().init(this);
        BinderPool.getInstance(this);
        MessengerManager.getInstance().init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
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

    private void initLoadingAndRetryLayout() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry_view;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading_view;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty_view;
    }
}
