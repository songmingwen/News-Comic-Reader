package com.song.sunset.utils.rxjava;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/10
 */
public class RxBus {
    private static final RxBus sRxBus = new RxBus();

    public static RxBus getInstance() {
        return sRxBus;
    }

    private PublishSubject<Object> mSubject = PublishSubject.create();

    private RxBus() {
    }

    public void post(Object object) {
        mSubject.onNext(object);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mSubject.ofType(eventType);
    }

    public Observable<Object> toObservable() {
        return mSubject.hide();
    }

    public boolean hasObservers() {
        return mSubject.hasObservers();
    }
}
