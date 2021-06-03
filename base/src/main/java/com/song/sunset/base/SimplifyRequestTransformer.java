package com.song.sunset.base;

import androidx.annotation.Nullable;

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
import retrofit2.Response;

/**
 * @author songmingwen
 * @description 用于简化 RxJava 网络请求
 * @since 2020/2/26
 */
public class SimplifyRequestTransformer<T> implements ObservableTransformer<Response<T>, T>,
        FlowableTransformer<Response<T>, T>,
        SingleTransformer<Response<T>, T>,
        MaybeTransformer<Response<T>, T>,
        CompletableTransformer {

    private LifecycleTransformer<Response<T>> mLifecycleTransformer;

    public SimplifyRequestTransformer() {
        this(null);
    }

    public SimplifyRequestTransformer(@Nullable LifecycleTransformer<Response<T>> lifecycleTransformer) {
        mLifecycleTransformer = lifecycleTransformer;
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        if (mLifecycleTransformer != null) {
            upstream = upstream.compose(mLifecycleTransformer);
        } else {
            upstream = upstream.compose(new SchedulerTransformer<>());
        }
        return upstream;
    }

    @Override
    public Publisher<T> apply(Flowable<Response<T>> upstream) {
        if (mLifecycleTransformer != null) {
            upstream = upstream.compose(new NetworkLifecycleTransformer<>(mLifecycleTransformer));
        } else {
            upstream = upstream.compose(new SchedulerTransformer<>());
        }
        return upstream.flatMap(response -> {
            if (response.isSuccessful()) {
                T t = response.body();
                if (t == null) {
                    return Flowable.error(new IllegalArgumentException("解析出为 null"));
                } else {
                    return Flowable.just(t);
                }
            } else {
                return Flowable.error(new RetrofitAPIError(response));
            }
        });
    }

    @Override
    public MaybeSource<T> apply(Maybe<Response<T>> upstream) {
        if (mLifecycleTransformer != null) {
            upstream = upstream.compose(new NetworkLifecycleTransformer<>(mLifecycleTransformer));
        } else {
            upstream = upstream.compose(new SchedulerTransformer<>());
        }
        return upstream.flatMap(response -> {
            if (response.isSuccessful()) {
                T t = response.body();
                if (t == null) {
                    return Maybe.error(new IllegalArgumentException("解析出为 null"));
                } else {
                    return Maybe.just(t);
                }
            } else {
                return Maybe.error(new RetrofitAPIError(response));
            }
        });
    }

    @Override
    public ObservableSource<T> apply(Observable<Response<T>> upstream) {
        if (mLifecycleTransformer != null) {
            upstream = upstream.compose(new NetworkLifecycleTransformer<>(mLifecycleTransformer));
        } else {
            upstream = upstream.compose(new SchedulerTransformer<>());
        }
        return upstream.flatMap(response -> {
            if (response.isSuccessful()) {
                T t = response.body();
                if (t == null) {
                    return Observable.error(new IllegalArgumentException("解析出为 null"));
                } else {
                    return Observable.just(t);
                }
            } else {
                return Observable.error(new RetrofitAPIError(response));
            }
        });
    }

    @Override
    public SingleSource<T> apply(Single<Response<T>> upstream) {
        if (mLifecycleTransformer != null) {
            upstream = upstream.compose(new NetworkLifecycleTransformer<>(mLifecycleTransformer));
        } else {
            upstream = upstream.compose(new SchedulerTransformer<>());
        }
        return upstream.flatMap(response -> {
            if (response.isSuccessful()) {
                T t = response.body();
                if (t == null) {
                    return Single.error(new IllegalArgumentException("解析出为 null"));
                } else {
                    return Single.just(t);
                }
            } else {
                return Single.error(new RetrofitAPIError(response));
            }
        });
    }
}
