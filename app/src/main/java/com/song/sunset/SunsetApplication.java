package com.song.sunset;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.song.sunset.utils.CrashHandler;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.MessengerManager;
import com.song.sunset.utils.PushManager;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.utils.AppConfig;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class SunsetApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        AppConfig.setApp(this);

        GreenDaoUtil.initGreenDao();

        initLoadingAndRetryLayout();

//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(this, OkHttpClient.INSTANCE.getOkHttpClient())
//                .build();

        Fresco.initialize(this, FrescoUtil.getDefaultImagePipelineConfig(this));
        CrashHandler.getInstance().init(this);
        PushManager.getInstance().init(this);
        MessengerManager.getInstance().init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initLoadingAndRetryLayout() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry_view;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading_view;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty_view;
    }
}
