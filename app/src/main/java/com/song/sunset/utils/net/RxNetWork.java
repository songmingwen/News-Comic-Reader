package com.song.sunset.utils.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AndroidException;
import android.view.View;

import com.song.sunset.base.net.NetWorkUtils;
import com.song.sunset.base.AppConfig;
import com.song.sunset.base.rxjava.RxBus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * @author songmingwen
 * @description 网络变化监听
 * @since 2020/3/10
 */
public enum RxNetWork {

    INSTANCE;

    public static final int TYPE_NONE = Integer.MAX_VALUE;
    public static final int TYPE_UNKNOWN = Integer.MAX_VALUE - 1;

    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @IntDef({ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI, TYPE_UNKNOWN, TYPE_NONE})
    public @interface NetworkType {
    }

    public class ConnectInfo {
        private int mNetworkType;
        private boolean mIsConnected;

        ConnectInfo(@NetworkType int networkType, boolean isConnected) {
            mNetworkType = networkType;
            mIsConnected = isConnected;
        }

        @NetworkType
        public int getNetworkType() {
            return mNetworkType;
        }

        public boolean isConnected() {
            return mIsConnected;
        }
    }

    private boolean mIsMobileConnected;
    private boolean mIsWifiConnected;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                realHandleReceive(this, context, intent);
            } catch (AndroidException | SecurityException e) {
                // remote 有可能会挂，所以在这里 catch 住所有的 AndroidException，事实上很多与系统的交互都会挂
            }
        }
    };

    private void realHandleReceive(BroadcastReceiver receiver, Context context, Intent intent) throws AndroidException {
        if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            return;
        }

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // noinspection ConstantConditions
        NetworkInfo info = manager.getActiveNetworkInfo();
        ConnectInfo connectInfo;
        if (info == null) {
            mIsWifiConnected = false;
            connectInfo = new ConnectInfo(TYPE_NONE, false);
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            mIsMobileConnected = info.isConnected();
            connectInfo = new ConnectInfo(ConnectivityManager.TYPE_MOBILE, mIsMobileConnected);
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            mIsWifiConnected = info.isConnected();
            connectInfo = new ConnectInfo(ConnectivityManager.TYPE_WIFI, mIsWifiConnected);
        } else {
            // 这种情况下，通过 util 在查询一把网络状态，最后确认一次
            int type = TYPE_NONE;
            switch (NetWorkUtils.getNetworkState(AppConfig.getApp())) {
                case NetWorkUtils.NETWORK_MOBILE:
                case NetWorkUtils.NETWORK_2G:
                case NetWorkUtils.NETWORK_3G:
                case NetWorkUtils.NETWORK_4G:
                    type = ConnectivityManager.TYPE_MOBILE;
                    break;
                case NetWorkUtils.NETWORK_ETHERNET:
                case NetWorkUtils.NETWORK_WIFI:
                    type = ConnectivityManager.TYPE_WIFI;
                    break;
                default:
                    if (info.isConnected()) {
                        type = TYPE_UNKNOWN;
                    }
                    break;
            }
            connectInfo = new ConnectInfo(type, info.isConnected());
        }
        // 将网络状态发布出去
        RxBus.getInstance().post(connectInfo);
    }

    private int registerTimes = 0;

    /**
     * 务必在 Activity.onCreate() 时调用，调用一次即可。 register 之后系统会自动触发一次网络判断
     */
    public void register(@NonNull Context context) {
        if (registerTimes == 0) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.getApplicationContext().registerReceiver(mReceiver, filter);
        }
        registerTimes += 1;
    }

    /**
     * 务必在 Activity.onDestroy() 时调用，调用一次即可
     */
    public void unregister(@NonNull Context context) {
        if (registerTimes == 1) {
            // unregisterReceiver 后 mReceiver 中的 mPendingResult 还是会持有 Context，
            // 执行一下 goAsync 可去除之且无副作用
            mReceiver.goAsync();
            context.getApplicationContext().unregisterReceiver(mReceiver);
        }
        registerTimes -= 1;
        if (registerTimes < 0) {
            registerTimes = 0;
        }
    }

    public boolean isMobileConnected() {
        return mIsMobileConnected;
    }

    public boolean isWifiConnected() {
        return mIsWifiConnected;
    }

    public boolean hasConnection() {
        return mIsMobileConnected || mIsWifiConnected;
    }

    @NonNull
    public Observable<ConnectInfo> onConnectionChangedForever() {
        return RxBus.getInstance().toObservableForeverOrManualDispose(ConnectInfo.class);
    }

    /**
     * 将网络变化绑定生命周期
     */
    @NonNull
    public Observable<ConnectInfo> onConnectionChanged(@NonNull LifecycleOwner bindTo) {
        return RxBus.getInstance().toObservable(ConnectInfo.class, bindTo);
    }

    /**
     * 将网络变化绑定生命周期
     */
    @NonNull
    public Observable<ConnectInfo> onConnectionChanged(@NonNull LifecycleOwner bindTo, @NonNull Lifecycle.Event until) {
        return RxBus.getInstance().toObservable(ConnectInfo.class, bindTo, until);
    }

    /**
     * 将网络变化绑定到一个 View 上
     */
    @NonNull
    public Observable<ConnectInfo> onConnectionChanged(View view) {
        return RxBus.getInstance().toObservable(ConnectInfo.class, view);
    }
}
