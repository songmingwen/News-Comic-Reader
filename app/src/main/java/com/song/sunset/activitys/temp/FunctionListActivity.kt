package com.song.sunset.activitys.temp

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.bubblepicker.BubbleActivity
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.song.game.soaring.SoaringBattleActivity.Companion.BATTLE_EXTRA
import com.song.game.soaring.SoaringBattleActivity.Companion.obtainBundle
import com.song.scankit.QRCodeActivity
import com.song.sunset.R
import com.song.sunset.activitys.PhoenixVideoActivity
import com.song.sunset.activitys.opengl.OpenGLListActivity
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.beans.MusicInfo
import com.song.sunset.phoenix.bean.VideoDetailBean
import com.song.sunset.services.impl.BinderPoolImpl
import com.song.sunset.services.impl.MusicCallBackListenerImpl
import com.song.sunset.services.impl.MusicGetterImpl
import com.song.sunset.services.managers.BinderPool
import com.song.sunset.utils.RelayTest
import com.song.sunset.utils.ScreenUtils
import com.song.sunset.utils.ViewUtil
import com.song.sunset.utils.preinstall.DefaultPreinstallHandler
import com.song.sunset.utils.preinstall.HuaweiPreinstallHandler
import com.song.sunset.utils.preinstall.VivoPreinstallHandler
import com.song.sunset.utils.preinstall.XiaomiPreinstallHandler
import com.song.sunset.utils.process.AndroidProcesses
import com.song.sunset.widget.fireworks.BitmapProvider
import com.song.sunset.widget.fireworks.FireworksView
import kotlinx.android.synthetic.main.activity_function_list.*

class FunctionListActivity : BaseActivity() {

    private fun LinearLayout.addButtonList() {
        addButton("Soaring") { ARouter.getInstance().build("/song/soaring/home").navigation() }
        addButton("battle") { toBattle() }
        addButton("🎆") { button -> showFireworks(button) }
        addButton("test") { TempTestActivity.start(this@FunctionListActivity) }
        addButton("ViewDragHelper") { ViewDragHelperActivity.start(this@FunctionListActivity) }
        addDanmu()
        addButton("Fresco XML params display") { FrescoXMLActivity.start(this@FunctionListActivity) }
        addButton("Fresco post processor display") { FrescoProcessorActivity.start(this@FunctionListActivity) }
        addButton("Glide post processor display") { TransTestActivity.start(this@FunctionListActivity) }
        addButton("ScanKit") { ARouter.getInstance().build("/scan/list").navigation() }
        addButton("QR code") { QRCodeActivity.start(this@FunctionListActivity) }
        addButton("RxJava") { RxjavaActivity.start(this@FunctionListActivity) }
        addButton("Reflection") { ReflectionActivity.start(this@FunctionListActivity) }
        addButton("DynamicProxy") { DynamicProxyActivity.start(this@FunctionListActivity) }
        addButton("CoordinatorLayout") { ScrollingActivity.start(this@FunctionListActivity) }
        addButton("SecondFloor") { SecondFloorActivity.start(this@FunctionListActivity) }
        addButton("binderPool") { useBinderPool() }
        addButton("openGL") { OpenGLListActivity.start(this@FunctionListActivity) }
        addButton("neural") { ARouter.getInstance().build("/neural/neural").navigation() }
        addButton("billiards") { BilliardsActivity.start(this@FunctionListActivity) }
        addButton("MotionLayout") { MotionLayoutActivity.start(this@FunctionListActivity) }
        addButton("Bubble") { BubbleActivity.start(this@FunctionListActivity) }
        addButton("Lottie") { LottieActivity.start(this@FunctionListActivity) }
        addButton("WorkManager") { WorkManagerActivity.start(this@FunctionListActivity) }
        addButton("GlobalFlow") { GlobalFlowActivity.start(this@FunctionListActivity) }
        addButton("Camera") { CameraActivity.start(this@FunctionListActivity) }
        addButton("ServiceProvider") { ServiceProviderActivity.start(this@FunctionListActivity) }
        addButton("CenteredImageSpan") { CenteredImageSpanActivity.start(this@FunctionListActivity) }
        addButton("RelayTest") { RelayTest.testRelay() }
        addButton("MMKVTest") { RelayTest.testMMKV() }
        addButton("Xposed") { showResult() }

    }

    private fun showResult() {
        //xposed 会 hook 此方法
    }

    private fun toBattle() {
        ARouter.getInstance().build("/song/soaring/battle")
                .withBundle(BATTLE_EXTRA, obtainBundle("乔峰", "虚竹",
                        "降龙十八掌", "龙爪手"))
                .navigation()
    }

    private var mFireworksView: FireworksView? = null

    private val mIMusicCallBackListener = object : MusicCallBackListenerImpl() {
        @Throws(RemoteException::class)
        override fun success(list: List<MusicInfo>) {
            super.success(list)
            Log.i(TAG + "MainActivity：", list.toString())
        }

        @Throws(RemoteException::class)
        override fun failure() {
            Log.i(TAG + "MainActivity：", "get music failure")
        }
    }

    private val fireworksProvider: BitmapProvider.Provider
        get() = BitmapProvider.Builder(this)
                .setDrawableResArray(
                        arrayOf(R.drawable.fireworks_emoji001, R.drawable.fireworks_emoji002, R.drawable.fireworks_emoji003,
                                R.drawable.fireworks_emoji004, R.drawable.fireworks_emoji005, R.drawable.fireworks_emoji006,
                                R.drawable.fireworks_emoji007, R.drawable.fireworks_emoji008, R.drawable.fireworks_emoji009,
                                R.drawable.fireworks_emoji010, R.drawable.fireworks_emoji011, R.drawable.fireworks_emoji012,
                                R.drawable.fireworks_emoji013, R.drawable.fireworks_emoji014, R.drawable.fireworks_emoji015,
                                R.drawable.fireworks_emoji016, R.drawable.fireworks_emoji017, R.drawable.fireworks_emoji018,
                                R.drawable.fireworks_emoji019, R.drawable.fireworks_emoji020, R.drawable.fireworks_emoji021).toList())
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_list)

        mFireworksView = findViewById(R.id.fireworks_layout)

        ll_function_container.apply {
            addButtonList()
        }

        Log.i("屏幕旋转生命周期", "onCreate")
        Log.i("A -> B", "A : onCreate")

        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance())
    }

    override fun onStart() {
        super.onStart()
        Log.i("屏幕旋转生命周期", "onStart")
        Log.i("A -> B", "A : onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("A -> B", "A : onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("屏幕旋转生命周期", "onResume")
        Log.i("A -> B", "A : onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("屏幕旋转生命周期", "onPause")
        Log.i("A -> B", "A : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("屏幕旋转生命周期", "onStop")
        Log.i("A -> B", "A : onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("屏幕旋转生命周期", "onDestroy")
        Log.i("A -> B", "A : onDestroy")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i("屏幕旋转生命周期", "onConfigurationChanged")
    }

    private fun LinearLayout.addDanmu() {
        val videoDetailBean = VideoDetailBean()
        videoDetailBean.title = "搞笑视频"
        videoDetailBean.video_url = "https://vd2.bdstatic.com//mda-ke9s31k9fwjvvuac//v1-cae//mda-ke9s31k9fwjvvuac.mp4"
        addButton("弹幕") { PhoenixVideoActivity.start(this@FunctionListActivity, videoDetailBean) }
    }

    private fun showFireworks(view: View) {
        mFireworksView!!.provider = fireworksProvider

        if (mFireworksView == null) {
            return
        }
        val itemPosition = IntArray(2)
        view.getLocationOnScreen(itemPosition)
        val x = itemPosition[0] + view.width / 2
        val y = itemPosition[1] - ScreenUtils.dp2Px(this, 24f)
        mFireworksView!!.launch(x, y.toInt())
    }

    /**
     * 使用binderPool过去对应的binder并且执行相应的方法（回调中获取结果，[异步]）
     */
    private fun useBinderPool() {
        val iBinder = BinderPool.getInstance().queryBinder(BinderPoolImpl.BINDER_GET_MUSIC)
        val iMusicGetter = MusicGetterImpl.asInterface(iBinder)
        try {
            iMusicGetter.getMusicList(mIMusicCallBackListener)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

    /**
     * 责任链
     */
    private fun testChain() {
        val huaweiPreinstallHandler = HuaweiPreinstallHandler()
        val xiaomiPreinstallHandler = XiaomiPreinstallHandler()
        val vivoPreinstallHandler = VivoPreinstallHandler()
        val defaultPreinstallHandler = DefaultPreinstallHandler()
        huaweiPreinstallHandler.setNextHandler(xiaomiPreinstallHandler)
        xiaomiPreinstallHandler.setNextHandler(vivoPreinstallHandler)
        vivoPreinstallHandler.setNextHandler(defaultPreinstallHandler)
        Log.e("preinstall", huaweiPreinstallHandler.preinstallInfo)
    }

    private fun getFactorial(endNum: Long): Long {
        return if (endNum <= 1) {
            1
        } else {
            getFactorial(endNum - 1) * endNum
        }
    }

    /**
     * Java没有实现编译器尾递归的优化
     */
    private fun getOrderPlus(endNum: Long): Long {
        return if (endNum == 1L) 1 else getOrderPlus(endNum, 1)
    }

    private fun getOrderPlus(endNum: Long, sum: Long): Long {
        return if (endNum == 1L) sum else getOrderPlus(endNum - 1, sum + endNum)
    }

    private fun PrintProcess() {
        val processes = AndroidProcesses.getRunningAppProcesses()

        for (process in processes) {
            Log.d("process_song", process.packageName)
        }
    }

    private fun getTopApp() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val m = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            if (m != null) {
                val now = System.currentTimeMillis()
                //获取600秒之内的应用数据
                val stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 600 * 1000, now)
                Log.i("song", "Running app number in last 600 seconds : " + stats.size)

                //取得最近运行的一个app，即当前运行的app
                if (stats.isNotEmpty()) {
                    for (i in stats.indices) {
                        Log.i("song", "top running app is : " + stats[i].packageName)
                    }
                }

            }
        }
    }

    companion object {

        val TAG: String = FunctionListActivity::class.java.name

        fun start(context: Context) {
            val starter = Intent(context, FunctionListActivity::class.java)
            context.startActivity(starter)
        }
    }
}

fun LinearLayout.addButton(title: String, onClick: (View) -> Unit): Button {
    val button = Button(context).apply {
        text = title
        textSize = 15f
        setTextColor(resources.getColor(R.color.white))
        setBackgroundResource(R.drawable.shape_interest_selection_blue_bg)
        setOnClickListener { onClick(this) }
    }
    val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    params.topMargin = ViewUtil.dip2px(5f)
    params.bottomMargin = ViewUtil.dip2px(5f)
    addView(button, params)
    return button
}

//        startActivity(new Intent(MainActivity.this, SubScaleViewActivity.class));

//        PushManager.getInstance().connect();
//        PushManager.getInstance().sendMusicInfo(MusicLoader.instance().getMusicList().get(0));
//        MessengerManager.getInstance().sendMessage();
//
//        MusicGetterManager.getInstance().setMusicCallBackListener(new MusicGetterManager.MusicCallBackListener() {
//            @Override
//            public void success(List<MusicInfo> list) {
//                Log.i(TAG + "callback", list.toString());
//            }
//
//            @Override
//            public void failure() {
//                Log.i(TAG, "false");
//            }
//        });
//        MusicGetterManager.getInstance().getMusicLists();
//        useBinderPool();
//        switchDayNightMode();
