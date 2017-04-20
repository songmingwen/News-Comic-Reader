package com.song.sunset.utils.rxjava;

import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by songmw3 on 2016/12/6.
 * E-mail:z53520@qq.com
 */

public class RxUtil {

    public static <T> void comicSubscribe(Observable<BaseBean<T>> observable, final RetrofitCallback<T> retrofitCallback) {
        observable
                .map(new Func1<BaseBean<T>, T>() {
                    @Override
                    public T call(BaseBean<T> tBaseBean) {
                        if (tBaseBean == null ||
                                tBaseBean.data == null ||
                                tBaseBean.data.returnData == null ||
                                tBaseBean.data.stateCode == 0) {
                            return null;
                        } else {
                            return tBaseBean.data.returnData;
                        }
                    }
                })
                .compose(RxUtil.<T>rxSchedulerHelper())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        retrofitCallback.onFailure(-1, "服务器错误");
                    }

                    @Override
                    public void onNext(T t) {
                        if (t == null) retrofitCallback.onFailure(-1, "无数据");
                        else retrofitCallback.onSuccess(t);
                    }
                });
    }

    public static <T> void phoenixNewsSubscribe(Observable<List<T>> observable, final RetrofitCallback<T> retrofitCallback) {
        observable
                .compose(RxUtil.<List<T>>rxSchedulerHelper())
                .subscribe(new Subscriber<List<T>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        retrofitCallback.onFailure(-1, "服务器错误");
                    }

                    @Override
                    public void onNext(List<T> t) {
                        if (t == null) {
                            return;
                        }
                        retrofitCallback.onSuccess(t.get(0));
                    }
                });
    }

    /**
     * compose简化线程
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }
}
