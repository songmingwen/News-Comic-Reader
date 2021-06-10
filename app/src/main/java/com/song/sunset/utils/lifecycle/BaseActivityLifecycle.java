package com.song.sunset.utils.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.song.sunset.base.AppConfig;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import io.reactivex.Observable;

/**
 * @author songmingwen
 * @description
 * @since 2020/3/10
 */
public abstract class BaseActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    //<editor-fold desc="执行在UI线程立即回调的方法">

    /**
     * 第一个 Activity onCreate，执行在UI线程
     */
    @UiThread
    public void onFirstCreateSync(Activity firstActivity) {
    }

    /**
     * 应用进入前台，执行在UI线程
     */
    @UiThread
    public void onGlobalStartSync(Activity currentActivity) {
    }

    /**
     * 应用回到前台，执行在UI线程
     */
    @UiThread
    public void onGlobalResumeSync(Activity currentActivity) {
    }

    /**
     * 应用进入后台，执行在UI线程
     */
    @UiThread
    public void onGlobalPauseSync(Activity currentActivity) {
    }

    /**
     * 应用进入后台，执行在UI线程
     */
    @UiThread
    public void onGlobalStopSync(Activity currentActivity) {
    }

    /**
     * 最后一个 Activity 销毁
     */
    @UiThread
    public void onLastDestroySync(Activity lastActivity) {
    }
    //</editor-fold>

    /**
     * 第一个 Activity 可见
     */
    @WorkerThread
    public void onFirstCreate(Activity firstActivity) {
    }

    /**
     * 整个应用回到前台
     */
    @WorkerThread
    public void onGlobalResume(Activity currentActivity) {
    }

    /**
     * 整个应用到后台
     * 注意：此方法存在微小的延迟
     */
    @WorkerThread
    public void onGlobalPause(Activity currentActivity) {
    }

    @WorkerThread
    public void onGlobalStart(Activity currentActivity) {
    }

    @WorkerThread
    public void onGlobalStop(Activity currentActivity) {
    }

    /**
     * 最后一个 Activity 销毁
     */
    @WorkerThread
    public void onLastDestroy(Activity lastActivity) {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public Observable<LifecycleEvent> toObservable() {
        return LifecycleManager.Companion.getLifecyclePublisher().hide();
    }

    public Observable<LifecycleEvent> toObservable(LifecycleEvent event) {
        return LifecycleManager.Companion.getLifecyclePublisher().hide().filter(event::equals);
    }

    /**
     * @return 当前活跃的 activity context，如果不在前台则返回 application
     */
    @NonNull
    public Context getContext() {
        if (LifecycleManager.Companion.getSCurrentActivity().get() != null) {
            return LifecycleManager.Companion.getSCurrentActivity().get();
        } else {
            return AppConfig.getApp();
        }
    }

    /**
     * @return 当前活跃的 activity context
     */
    @Nullable
    public <T extends Activity> T getActivity() {
        if (LifecycleManager.Companion.getSCurrentActivity().get() != null) {
            return (T) LifecycleManager.Companion.getSCurrentActivity().get();
        } else {
            return null;
        }
    }

    public enum LifecycleEvent {
        FIRST_CREATE, GLOBAL_START, GLOBAL_STOP, GLOBAL_RESUME, GLOBAL_PAUSE, LAST_DESTROY
    }
}
