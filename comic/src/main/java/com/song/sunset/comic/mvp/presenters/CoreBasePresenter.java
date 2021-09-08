package com.song.sunset.comic.mvp.presenters;

/**
 * Created by hpw on 16/10/28.
 */

public abstract class CoreBasePresenter<M, V> {
    public M mModel;
    public V mView;

    public void attachVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
        mView = null;
        mModel = null;
    }

    public abstract void onStart();
}
