package com.song.sunset.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by Song on 2016/12/7.
 * Email:z53520@qq.com
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static Object lock = new Object();

    private CrashHandler() {
        // Empty Constractor
    }

    private static CrashHandler mCrashHandler;
    private Context mContext;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public static CrashHandler getInstance() {
        synchronized (lock) {
            if (mCrashHandler == null) {
                synchronized (lock) {
                    if (mCrashHandler == null) {
                        mCrashHandler = new CrashHandler();
                    }
                }
            }
            return mCrashHandler;
        }
    }

    /* 初始化 */
    public void init(Context context) {
        this.mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        if (isHandler(ex)) {
            handlerException(ex);
        } else {
            defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }

    private boolean isHandler(Throwable ex) {
        if (ex == null) {
            return false;
        }
        return true;
    }

    private void handlerException(final Throwable ex) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //异常处理
//                Looper.prepare();
//
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
//
//                Looper.loop();
            }
        }).start();
    }
}
