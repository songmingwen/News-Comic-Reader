package com.song.sunset.services.impl;


import android.os.RemoteException;
import android.util.Log;

import com.song.sunset.IMusicCallBackListener;
import com.song.sunset.beans.MusicInfo;

import java.util.List;

/**
 * Created by Song on 2018/1/5 0005.
 * E-mail: z53520@qq.com
 */

public class MusicCallBackListenerImpl extends IMusicCallBackListener.Stub {

    public static final String TAG = MusicCallBackListenerImpl.class.getName();

    @Override
    public void success(List<MusicInfo> list) throws RemoteException {
        Log.i(TAG, "success: " + list.toString());
    }

    @Override
    public void failure() throws RemoteException {

    }
}
