package com.song.sunset.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.song.sunset.IMusicCallBackListener;
import com.song.sunset.IMusicGetter;
import com.song.sunset.beans.MusicInfo;
import com.song.sunset.utils.MusicLoader;
import com.song.sunset.utils.rxjava.RxUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MusicGetterService extends Service {

    public static final String TAG = MusicGetterService.class.getName();

    final RemoteCallbackList<IMusicCallBackListener> mCallBacks = new RemoteCallbackList<>();

    Binder mBinder = new IMusicGetter.Stub() {
        @Override
        public void getMusicList(final IMusicCallBackListener listener) throws RemoteException {
            Log.i(TAG, "in");
            mCallBacks.register(listener);
            Observable.just(0)
                    .map(new Func1<Object, List<MusicInfo>>() {
                        @Override
                        public List<MusicInfo> call(Object o) {
                            Log.i(TAG, "get");
                            return MusicLoader.instance().getMusicList();
                        }
                    })
                    .filter(new Func1<List<MusicInfo>, Boolean>() {
                        @Override
                        public Boolean call(List<MusicInfo> musicInfo) {
                            Log.i(TAG, "filter");
                            return musicInfo != null;
                        }
                    })
                    .compose(RxUtil.<List<MusicInfo>>rxSchedulerHelper())
                    .subscribe(new Subscriber<List<MusicInfo>>() {
                        @Override
                        public void onCompleted() {
                            Log.i(TAG, "complete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "error");
                            final int N = mCallBacks.beginBroadcast();
                            for (int i = 0; i < N; i++) {
                                try {
                                    mCallBacks.getBroadcastItem(i).failure();
                                } catch (RemoteException ignored) {
                                }
                            }
                            mCallBacks.finishBroadcast();
                        }

                        @Override
                        public void onNext(List<MusicInfo> musicInfo) {
                            Log.i(TAG, "next");
                            final int N = mCallBacks.beginBroadcast();
                            for (int i = 0; i < N; i++) {
                                try {
                                    mCallBacks.getBroadcastItem(i).success(musicInfo);
                                } catch (RemoteException ignored) {
                                }
                            }
                            mCallBacks.finishBroadcast();
                        }
                    });
        }
    };

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