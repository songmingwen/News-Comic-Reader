package com.song.sunset.services.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.song.sunset.IPush;
import com.song.sunset.beans.MusicInfo;
import com.song.sunset.services.PushService;

/**
 * Created by Song on 2017/6/7 0007.
 * E-mail: z53520@qq.com
 */

public class PushManager {

    private static final String TAG = "PushManager.class";

    private IPush mIPush;

    private PushManager() {
    }

    private static PushManager mInstance = new PushManager();

    public static PushManager getInstance() {
        return mInstance;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPush = IPush.Stub.asInterface(service);
            //连接成功调动
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接调用
        }
    };

    public void init(Context application) {
        Intent binderIntent = new Intent(application, PushService.class);
        application.bindService(binderIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void destory(Context application){
        application.unbindService(mServiceConnection);
    }

    //通过AIDL远程调用
    public void connect() {
        try {
            mIPush.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMusicInfo(MusicInfo musicInfo) {
        try {
            mIPush.sendMusicInfo(musicInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
