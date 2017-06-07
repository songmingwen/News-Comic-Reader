package com.song.sunset.utils;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.song.sunset.IHandler;
import com.song.sunset.beans.MusicInfo;
import com.song.sunset.beans.User;
import com.song.sunset.services.PushService;

/**
 * Created by Song on 2017/6/7 0007.
 * E-mail: z53520@qq.com
 */

public class PushManager {

    private static final String TAG = "PushManager.class";

    private IHandler mIHandler;

    private PushManager() {
    }

    private static PushManager mInstance = new PushManager();

    public static PushManager getInstance() {
        return mInstance;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIHandler = IHandler.Stub.asInterface(service);
            //连接成功调动
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接调用
        }
    };

    public void init(Application application) {
        Intent binderIntent = new Intent(application, PushService.class);
        application.bindService(binderIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    //通过AIDL远程调用
    public void connect() {
        try {
            mIHandler.connect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendMusicInfo(MusicInfo musicInfo) {
        try {
            mIHandler.sendMusicInfo(musicInfo);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
