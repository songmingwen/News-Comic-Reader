package com.song.sunset

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Process
import android.text.TextUtils
import androidx.multidex.MultiDexApplication
import androidx.startup.AppInitializer
import com.alibaba.android.arouter.launcher.ARouter
import com.google.firebase.FirebaseApp
import com.song.sunset.activitys.temp.GlobalFlowActivity.Companion.hideView
import com.song.sunset.activitys.temp.GlobalFlowActivity.Companion.showGlobalFlowView
import com.song.sunset.base.AppConfig
import com.song.sunset.base.startup.ImageLibInitializer
import com.song.sunset.base.startup.NetInitializer
import com.song.sunset.base.utils.SPUtils
import com.song.sunset.comic.utils.GreenDaoUtil
import com.song.sunset.services.managers.BinderPool
import com.song.sunset.services.managers.MessengerManager
import com.song.sunset.services.managers.MusicGetterManager
import com.song.sunset.services.managers.PushManager
import com.song.sunset.startup.CoilInitializer
import com.song.sunset.utils.lifecycle.BaseActivityLifecycle
import com.song.sunset.utils.lifecycle.LifecycleManager
import com.tencent.mmkv.MMKV
import io.flutter.view.FlutterMain

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
class SunsetApplication : MultiDexApplication() {

    private var isMainProcess = false

    override fun onCreate() {
        super.onCreate()
        initProcess()
        if (isMainProcess) {
            initInMainProcess()
        }
    }

    private fun initInMainProcess() {
        AppConfig.setApp(this)

        setBuildConfig()

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        if (SPUtils.getBooleanByName(this, SPUtils.APP_FIRST_INSTALL, true)) {
            SPUtils.setBooleanByName(this, SPUtils.APP_FIRST_INSTALL, true)
        }
        GreenDaoUtil.initGreenDao()

        MMKV.initialize(this)

//        CrashHandler.getInstance().init();
        PushManager.getInstance().init(this)
        BinderPool.getInstance()
        MessengerManager.getInstance().init(this)
        MusicGetterManager.getInstance().init(this)

//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//            LeakCanary.install(this);
//        }

        //生命周期监听
        addLifecycleListener()
        FlutterMain.startInitialization(this)

        AppInitializer.getInstance(applicationContext).initializeComponent(CoilInitializer::class.java)
        AppInitializer.getInstance(applicationContext).initializeComponent(NetInitializer::class.java)
        AppInitializer.getInstance(applicationContext).initializeComponent(ImageLibInitializer::class.java)

        FirebaseApp.initializeApp(this)
    }

    private fun setBuildConfig() {
        com.song.sunset.utils.BuildConfig.APPLICATION_ID = BuildConfig.APPLICATION_ID
        com.song.sunset.utils.BuildConfig.DEBUG = BuildConfig.DEBUG
        com.song.sunset.utils.BuildConfig.BUILD_TYPE = BuildConfig.BUILD_TYPE
        com.song.sunset.utils.BuildConfig.FLAVOR = BuildConfig.FLAVOR
        com.song.sunset.utils.BuildConfig.VERSION_CODE = BuildConfig.VERSION_CODE
        com.song.sunset.utils.BuildConfig.VERSION_NAME = BuildConfig.VERSION_NAME
    }

    private fun addLifecycleListener() {
        val lifecycleManager = LifecycleManager(this)
        lifecycleManager.addLifeCycle("测试", object : BaseActivityLifecycle() {
            override fun onActivityResumed(activity: Activity) {
                super.onActivityResumed(activity)
                val store = MMKV.defaultMMKV()
                val show = store.getBoolean("show_global_flow", false)
                if (show) {
                    showGlobalFlowView(activity)
                } else {
                    hideView(activity)
                }
            }
        })
        lifecycleManager.init()
    }

    private fun initProcess() {
        val nProcessId = Process.myPid()
        val actvityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val procInfos = actvityManager.runningAppProcesses
        for (procInfo in procInfos) {
            if (nProcessId == procInfo.pid) {
                if (!TextUtils.isEmpty(procInfo.processName)) {
                    if (packageName == procInfo.processName) {
                        // check if it is main process.
                        isMainProcess = true
                    }
                }
            }
        }
    }
}