package com.song.sunset.services.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.song.sunset.IMusicCallBackListener;
import com.song.sunset.IMusicGetter;
import com.song.sunset.beans.MusicInfo;
import com.song.sunset.services.MusicGetterService;

import java.util.List;

/**
 * Created by Song on 2018/1/4 0004.
 * E-mail: z53520@qq.com
 */

public class MusicGetterManager {

    private MusicGetterManager() {
    }

    private IMusicGetter mIMusicGetter;

    private MusicCallBackListener mMusicCallBackListener;

    public void setMusicCallBackListener(MusicCallBackListener mMusicCallBackListener) {
        this.mMusicCallBackListener = mMusicCallBackListener;
    }

    private static MusicGetterManager mInstance = null;

    public static MusicGetterManager getInstance() {
        if (mInstance == null) {
            mInstance = new MusicGetterManager();
        }
        return mInstance;
    }

    public void init(Context context) {
        Intent binderIntent = new Intent(context, MusicGetterService.class);
        context.bindService(binderIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMusicGetter = IMusicGetter.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void getMusicLists() {
        try {
            mIMusicGetter.getMusicList(mIMusicCallBackListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IMusicCallBackListener mIMusicCallBackListener = new IMusicCallBackListener.Stub() {
        @Override
        public void success(List<MusicInfo> list) throws RemoteException {
            if (mMusicCallBackListener != null) {
                mMusicCallBackListener.success(list);
            }
        }

        @Override
        public void failure() throws RemoteException {
            if (mMusicCallBackListener != null) {
                mMusicCallBackListener.failure();
            }
        }
    };

    public interface MusicCallBackListener {
        void success(List<MusicInfo> list);

        void failure();
    }

}
