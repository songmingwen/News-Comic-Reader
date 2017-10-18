package com.song.sunset.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.song.sunset.services.impl.PushImpl;

/**
 * Created by Song on 2017/6/7 0007.
 * E-mail: z53520@qq.com
 */

public class PushService extends Service {

    public static String TAG = "PushService.class";

    Binder mBinder = new PushImpl();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
