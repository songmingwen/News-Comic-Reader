package com.song.sunset.activitys;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.R;
import com.song.sunset.utils.AppConfig;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class SunsetApplication extends Application {


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
//        CrashHandler.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initLoadingAndRetryLayout() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry_view;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading_view;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty_view;
    }
}
