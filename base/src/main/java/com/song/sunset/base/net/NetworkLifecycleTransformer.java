package com.song.sunset.base.net;

import com.trello.rxlifecycle3.LifecycleTransformer;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * @author songmingwen
 * @description 绑定生命周期并切换线程
 * compose(mLifecycleTransformer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
 * @since 2020/2/26
 */
public class NetworkLifecycleTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    private LifecycleTransformer<T> mLifecycleTransformer;

    public NetworkLifecycleTransformer(LifecycleTransformer<T> lifecycleTransformer) {
        mLifecycleTransformer = lifecycleTransformer;
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream.compose(mLifecycleTransformer).compose(new SchedulerTransformer<>());
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.compose(mLifecycleTransformer).compose(new SchedulerTransformer<>());
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        return upstream.compose(mLifecycleTransformer).compose(new SchedulerTransformer<>());
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.compose(mLifecycleTransformer).compose(new SchedulerTransformer<>());
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.compose(mLifecycleTransformer).compose(new SchedulerTransformer<>());
    }
}