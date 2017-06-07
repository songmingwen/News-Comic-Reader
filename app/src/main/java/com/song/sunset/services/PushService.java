package com.song.sunset.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.song.sunset.IHandler;
import com.song.sunset.beans.MusicInfo;

/**
 * Created by Song on 2017/6/7 0007.
 * E-mail: z53520@qq.com
 */

public class PushService extends Service {

    public static String TAG = "PushService.class";

    Binder mBinder = new IHandler.Stub() {

        @Override
        public void connect() throws RemoteException {
            Log.i(TAG, "connect");
        }

        @Override
        public void sendMusicInfo(MusicInfo musicInfo) throws RemoteException {
            Log.i(TAG, "sendUserInfo: " + musicInfo.toString());
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
