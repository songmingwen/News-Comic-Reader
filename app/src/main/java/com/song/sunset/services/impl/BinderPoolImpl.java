package com.song.sunset.services.impl;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;

import com.song.sunset.IBinderPool;

/**
 * Created by Song on 2017/10/18 0018.
 * E-mail: z53520@qq.com
 */

public class BinderPoolImpl extends IBinderPool.Stub {

    //在此处添加对应的binderCode
    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = BINDER_COMPUTE + 1;
    public static final int BINDER_PUSH = BINDER_SECURITY_CENTER + 1;
    public static final int BINDER_GET_MUSIC = BINDER_PUSH + 1;

    private Context mContext;

    public BinderPoolImpl(Context context) {
        mContext = context;
    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BINDER_COMPUTE:
                binder = new ComputeImpl();
                break;
            case BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl();
                break;
            case BINDER_PUSH:
                binder = new PushImpl();
                break;
            case BINDER_GET_MUSIC:
                binder = new MusicGetterImpl(mContext);
                break;
            default:
                break;
        }
        return binder;
    }

}
