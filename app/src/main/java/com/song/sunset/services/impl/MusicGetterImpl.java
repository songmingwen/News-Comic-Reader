package com.song.sunset.services.impl;

import android.content.Context;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.song.sunset.IMusicCallBackListener;
import com.song.sunset.IMusicGetter;
import com.song.sunset.beans.MusicInfo;
import com.song.sunset.services.MusicGetterService;
import com.song.sunset.utils.MusicLoader;
import com.song.sunset.utils.rxjava.RxUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Song on 2018/1/5 0005.
 * E-mail: z53520@qq.com
 */

public class MusicGetterImpl extends IMusicGetter.Stub {

    public static final String TAG = MusicGetterImpl.class.getName();

    private final RemoteCallbackList<IMusicCallBackListener> mCallBacks = new RemoteCallbackList<>();

    private Context mContext;

    public MusicGetterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void getMusicList(final IMusicCallBackListener listener) throws RemoteException {
        Log.i(TAG, "in");
        mCallBacks.register(listener);
        Observable.just(0)
                .map(new Func1<Object, List<MusicInfo>>() {
                    @Override
                    public List<MusicInfo> call(Object o) {
                        Log.i(TAG, "get");
                        return MusicLoader.instance().getMusicList(mContext);
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
                        Log.i(TAG, "error: " + e.getMessage());
                        final int N = mCallBacks.beginBroadcast();
                        for (int i = 0; i < N; i++) {
                            try {
                                mCallBacks.getBroadcastItem(i).failure();
                            } catch (RemoteException ignored) {
                            }
                        }
                        mCallBacks.finishBroadcast();
//                        try {
//                            listener.failure();
//                        } catch (RemoteException e1) {
//                            e1.printStackTrace();
//                        }
                    }

                    @Override
                    public void onNext(List<MusicInfo> musicInfo) {
                        Log.i(TAG, "next：" + musicInfo.toString());
                        final int N = mCallBacks.beginBroadcast();
                        for (int i = 0; i < N; i++) {
                            try {
                                Log.i(TAG, "success" + musicInfo.toString());
                                mCallBacks.getBroadcastItem(i).success(musicInfo);
                            } catch (RemoteException ignored) {
                                Log.i(TAG, "exception:" + ignored);
                                ignored.printStackTrace();
                            }
                        }
                        mCallBacks.finishBroadcast();
//                        try {
//                            listener.success(musicInfo);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
    }
}
