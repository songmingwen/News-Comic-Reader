package com.song.sunset.activitys.temp;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.song.kotlin.activitys.temp.DynamicProxyActivity;
import com.song.kotlin.activitys.temp.ReflectionActivity;
import com.song.sunset.IMusicCallBackListener;
import com.song.sunset.IMusicGetter;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.beans.MusicInfo;
import com.song.kotlin.interfaces.IOrigin;
import com.song.sunset.services.impl.BinderPoolImpl;
import com.song.sunset.services.impl.MusicCallBackListenerImpl;
import com.song.sunset.services.impl.MusicGetterImpl;
import com.song.sunset.services.managers.BinderPool;
import com.song.kotlin.utils.JdkDynamicProxy;
import com.song.kotlin.utils.OriginImpl;
import com.song.sunset.utils.preinstall.DefaultPreinstallHandler;
import com.song.kotlin.utils.preinstall.HuaweiPreinstallHandler;
import com.song.sunset.utils.preinstall.VivoPreinstallHandler;
import com.song.sunset.utils.preinstall.XiaomiPreinstallHandler;
import com.song.sunset.utils.process.AndroidProcesses;
import com.song.sunset.utils.process.models.AndroidAppProcess;
import com.song.sunset.widget.fireworks.BitmapProvider;
import com.song.sunset.widget.fireworks.FireworksView;

import java.util.List;

public class FunctionListActivity extends BaseActivity {

    public static final String TAG = FunctionListActivity.class.getName();

    private FireworksView mFireworksView;

    public static void start(Context context) {
        Intent starter = new Intent(context, FunctionListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_list);

        mFireworksView = (FireworksView) findViewById(R.id.fireworks_layout);

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
    }

    /**
     * Click 点击
     */
    public void showFrescoXML(View view) {
        FrescoXMLActivity.start(this);
    }

    /**
     * Click 点击
     */
    public void showFrescoProcessor(View view) {
        FrescoProcessorActivity.start(this);
    }

    /**
     * Click 点击
     */
    public void showFireworks(View view) {
        mFireworksView.setProvider(getFireworksProvider());

        if (mFireworksView == null) {
            return;
        }
        int[] itemPosition = new int[2];
        view.getLocationOnScreen(itemPosition);
        int x = itemPosition[0] + view.getWidth() / 2;
        int y = itemPosition[1];
        mFireworksView.launch(x, y);
    }

    /**
     * Click 点击
     */
    public void showRxJava(View view) {
        RxjavaActivity.start(this);
    }

    /**
     * Click 点击
     */
    public void showReflection(View view) {
        ReflectionActivity.Companion.start(this);
    }

    /**
     * Click 点击
     */
    public void showDynamicProxy(View view) {
        DynamicProxyActivity.Companion.start(this);
        JdkDynamicProxy proxy = new JdkDynamicProxy();
        IOrigin iOrigin = (IOrigin) proxy.bind(new OriginImpl());
    }

    /**
     * Click 点击
     */
    public void coordinatorLayout(View view) {
        ScrollingActivity.start(this);
    }

    /**
     * Click 点击
     */
    public void binderPool(View view) {
        useBinderPool();
    }

    private IMusicCallBackListener mIMusicCallBackListener = new MusicCallBackListenerImpl() {
        @Override
        public void success(List<MusicInfo> list) throws RemoteException {
            super.success(list);
            Log.i(TAG + "MainActivity：", list.toString());
        }

        @Override
        public void failure() throws RemoteException {
            Log.i(TAG + "MainActivity：", "get music failure");
        }
    };

    /**
     * 使用binderPool过去对应的binder并且执行相应的方法（回调中获取结果，[异步]）
     */
    private void useBinderPool() {
        IBinder iBinder = BinderPool.getInstance().queryBinder(BinderPoolImpl.BINDER_GET_MUSIC);
        IMusicGetter iMusicGetter = MusicGetterImpl.asInterface(iBinder);
        try {
            iMusicGetter.getMusicList(mIMusicCallBackListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 责任链
     */
    private void testChain() {
        HuaweiPreinstallHandler huaweiPreinstallHandler = new HuaweiPreinstallHandler();
        XiaomiPreinstallHandler xiaomiPreinstallHandler = new XiaomiPreinstallHandler();
        VivoPreinstallHandler vivoPreinstallHandler = new VivoPreinstallHandler();
        DefaultPreinstallHandler defaultPreinstallHandler = new DefaultPreinstallHandler();
        huaweiPreinstallHandler.setNextHandler(xiaomiPreinstallHandler);
        xiaomiPreinstallHandler.setNextHandler(vivoPreinstallHandler);
        vivoPreinstallHandler.setNextHandler(defaultPreinstallHandler);
        Log.e("preinstall", huaweiPreinstallHandler.getPreinstallInfo());
    }

    private long getFactorial(long endNum) {
        if (endNum <= 1) {
            return 1;
        } else {
            return getFactorial(endNum - 1) * endNum;
        }
    }

    /**
     * Java没有实现编译器尾递归的优化
     *
     * @param endNum
     * @return
     */
    private long getOrderPlus(long endNum) {
        return endNum == 1 ? 1 : getOrderPlus(endNum, 1);
    }

    private long getOrderPlus(long endNum, long sum) {
        return endNum == 1 ? sum : getOrderPlus(endNum - 1, sum + endNum);
    }

    public String getTaskList() {
        String apps = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return apps;
        }
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = getPackageManager();
        try {
            List<ActivityManager.RecentTaskInfo> list = am.getRecentTasks(64, 0);
            for (ActivityManager.RecentTaskInfo ti : list) {
                Intent intent = ti.baseIntent;
                ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
                if (resolveInfo != null) {
                    apps = apps.equals("") ? resolveInfo.loadLabel(pm) + "" : apps + "," + resolveInfo.loadLabel(pm);
                }
            }
            return apps;
        } catch (SecurityException se) {
            se.printStackTrace();
            return apps;
        }
    }

    private void PrintProcess() {
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();

        for (AndroidAppProcess process : processes) {
            Log.d("process_song", process.getPackageName());
        }
    }

    private void getTopApp() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取600秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 600 * 1000, now);
                Log.i("song", "Running app number in last 600 seconds : " + stats.size());

                //取得最近运行的一个app，即当前运行的app
                if (!stats.isEmpty()) {
                    for (int i = 0; i < stats.size(); i++) {
                        Log.i("song", "top running app is : " + stats.get(i).getPackageName());
                    }
                }

            }
        }
    }

    private BitmapProvider.Provider getFireworksProvider() {
        return new BitmapProvider.Builder(this)
                .setDrawableArray(new int[]{R.drawable.fireworks_emoji001, R.drawable.fireworks_emoji002, R.drawable.fireworks_emoji003,
                        R.drawable.fireworks_emoji004, R.drawable.fireworks_emoji005, R.drawable.fireworks_emoji006, R.drawable.fireworks_emoji007,
                        R.drawable.fireworks_emoji008, R.drawable.fireworks_emoji009, R.drawable.fireworks_emoji010, R.drawable.fireworks_emoji011,
                        R.drawable.fireworks_emoji012, R.drawable.fireworks_emoji013, R.drawable.fireworks_emoji014, R.drawable.fireworks_emoji015,
                        R.drawable.fireworks_emoji016, R.drawable.fireworks_emoji017, R.drawable.fireworks_emoji018, R.drawable.fireworks_emoji019,
                        R.drawable.fireworks_emoji020, R.drawable.fireworks_emoji021})
                .build();
    }
}
