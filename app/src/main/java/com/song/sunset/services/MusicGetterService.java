package com.song.sunset.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.song.sunset.services.impl.MusicGetterImpl;

public class MusicGetterService extends Service {

    public static final String TAG = MusicGetterService.class.getName();

    private Binder mBinder = new MusicGetterImpl(this);

    public MusicGetterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}