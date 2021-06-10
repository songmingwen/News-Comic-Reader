package com.song.sunset.base.rxjava;

import com.song.sunset.base.bean.BaseBean;
import com.song.sunset.base.net.RetrofitCallback;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Song on 2016/12/6.
 * E-mail:z53520@qq.com
 */

public class RxUtil {

    public static <T> void comicSubscribe(Observable<BaseBean<T>> observable, final RetrofitCallback<T> retrofitCallback) {
        observable
                .map(tBaseBean -> {
                    if (tBaseBean == null ||
                            tBaseBean.data == null ||
                            tBaseBean.data.returnData == null ||
                            tBaseBean.data.stateCode == 0) {
                        return null;
                    } else {
                        return tBaseBean.data.returnData;
                    }
                })
                .compose(getDefaultScheduler())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        if (t == null) retrofitCallback.onFailure(-1, "无数据");
                        else retrofitCallback.onSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        retrofitCallback.onFailure(-1, "服务器错误");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static <T> Disposable comic(Observable<BaseBean<T>> observable, final RetrofitCallback<T> retrofitCallback) {
        return observable
                .map(tBaseBean -> {
                    if (tBaseBean == null ||
                            tBaseBean.data == null ||
                            tBaseBean.data.returnData == null ||
                            tBaseBean.data.stateCode == 0) {
                        return null;
                    } else {
                        return tBaseBean.data.returnData;
                    }
                })
                .compose(getDefaultScheduler())
                .subscribe(o -> {
                    if (o == null) retrofitCallback.onFailure(-1, "无数据");
                    else retrofitCallback.onSuccess(o);
                }, throwable -> retrofitCallback.onFailure(-1, "服务器错误"));
    }

    public static <T> void phoenixNewsSubscribe(Observable<List<T>> observable, final RetrofitCallback<T> retrofitCallback) {
        Disposable disposable = observable
                .compose(getDefaultScheduler())
                .subscribe(ts -> {
                    if (ts == null) retrofitCallback.onFailure(-1, "无数据");
                    else retrofitCallback.onSuccess(ts.get(0));
                }, throwable -> {
//                        retrofitCallback.onFailure(-1, "服务器错误");
                });
    }

    /**
     * compose简化线程
     */
    public static <T> ObservableTransformer<T, T> getDefaultScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
