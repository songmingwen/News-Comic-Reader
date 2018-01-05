package com.song.sunset.services.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.util.Log;

import com.song.sunset.IBinderPool;
import com.song.sunset.services.BinderPoolService;
import com.song.sunset.utils.AppConfig;

/**
 * Binder连接池
 * 主要的实现在这里
 */
public class BinderPool {

    private static final String TAG = "BinderPool";

    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstance;
    private long mStart;

    private BinderPool() {
        super();
        //初始化的时候连接服务端Service
        connectBinderPoolService();
    }

    public static BinderPool getInstance() {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool();
                }
            }
        }
        return sInstance;
    }

    //这里会遇到到同步问题，所以会加上同步代码块
    private synchronized void connectBinderPoolService() {
        //绑定service
        Intent service = new Intent(AppConfig.getApp(), BinderPoolService.class);
        mStart = System.currentTimeMillis();
        AppConfig.getApp().bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d(TAG, "onServiceDisconnected");
            //关闭连接之后，在这里释放资源，也可以进行重连，暂不处理
        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder service) {
            long end = System.currentTimeMillis();
            Log.d(TAG, "onServiceConnected:" + (end - mStart) + "mills");

            //获取IBinderPool接口对象
            mBinderPool = IBinderPool.Stub.asInterface(service);

            try {
                //在这里加入了重连机制
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new DeathRecipient() {

        @Override
        public void binderDied() {
            Log.w(TAG, "binder died");
            //binder 死亡之后，让Binder连接池断开连接
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            //重新连接
            connectBinderPoolService();
        }
    };

    /**
     * 通过IBinderPool接口根据binderCode去获取对应的AIDL接口对象
     *
     * @param binderCode
     * @return
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

}
