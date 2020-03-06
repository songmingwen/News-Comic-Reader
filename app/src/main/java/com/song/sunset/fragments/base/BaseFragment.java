package com.song.sunset.fragments.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import io.reactivex.ObservableTransformer;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.utils.net.NetworkLifecycleTransformer;
import com.song.sunset.utils.net.SchedulerTransformer;
import com.song.sunset.utils.net.SimplifyRequestTransformer;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.components.support.RxFragment;

/**
 * Created by Song on 2016/8/27 0027.
 * Email:z53520@qq.com
 */
public class BaseFragment extends RxFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed() && isVisibleToUser) {
            //相当于Fragment的onResume
            visible2User();
        } else {
            //相当于Fragment的onPause
        }
    }

    /**
     * 返回一个只添加了线程切换的 Transform
     */
    public final <T> SchedulerTransformer<T> bindScheduler() {
        return new SchedulerTransformer<>();
    }

    /**
     * 1. 执行线程切换
     * 2. 绑定生命周期
     */
    public final <T> NetworkLifecycleTransformer<T> bindLifecycleAndScheduler() {
        return new NetworkLifecycleTransformer<>(bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    /**
     * 用于简化 RxJava 请求使用它会：
     * 1. 执行线程切换
     * 2. 绑定生命周期
     * 3. Response 剥离，将 Response[T] 中 T 的剥离
     */
    public final <T> ObservableTransformer<Response<T>, T> simplifyRequest() {
        return new SimplifyRequestTransformer<>(bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    protected void visible2User() {

    }
}
