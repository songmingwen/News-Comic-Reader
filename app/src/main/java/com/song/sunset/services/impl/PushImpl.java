package com.song.sunset.services.impl;

import android.os.RemoteException;
import android.util.Log;

import com.song.sunset.IPush;
import com.song.sunset.beans.MusicInfo;

/**
 * Created by Song on 2017/10/18 0018.
 * E-mail: z53520@qq.com
 */

public class PushImpl extends IPush.Stub {

    public static String TAG = "PushService.class";

    @Override
    public void connect() throws RemoteException {
        Log.i(TAG, "connect");
    }

    @Override
    public void sendMusicInfo(MusicInfo musicInfo) throws RemoteException {
        Log.i(TAG, "sendUserInfo: " + musicInfo.toString());
    }

}
