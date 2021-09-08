package com.song.sunset.comic.mvp.presenters;

import com.song.sunset.comic.bean.ComicDetailBean;
import com.song.sunset.comic.mvp.models.ComicDetailModel;
import com.song.sunset.base.rxjava.RxUtil;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.comic.mvp.views.ComicDetailView;

import io.reactivex.disposables.Disposable;

/**
 * Created by Song on 2016/12/8.
 * E-mail:z53520@qq.com
 */
public class ComicDetailPresenter extends CoreBasePresenter<ComicDetailModel, ComicDetailView> {

    private Disposable mDisposable;

    @Override
    public void onStart() {
    }

    public void showData(int comicId) {
        if (mModel == null) return;
        mDisposable = RxUtil.comic(mModel.getData(comicId), new RetrofitCallback<ComicDetailBean>() {
            @Override
            public void onSuccess(ComicDetailBean comicDetailBean) {
                if (mView == null) return;
                mView.setData(comicDetailBean);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                if (mView == null) return;
                mView.showError(errorMsg);
            }
        });
    }

    public void showCollectedStated(int comicId) {
        if (mView == null || mModel == null) return;
        mView.showCollected(mModel.isCollected(comicId), false);
    }

    public void changeCollectedState(final ComicDetailBean comicDetailBean) {
        if (mView == null || mModel == null) return;
        mModel.changeCollectedStateFromNet(comicDetailBean,
                add -> {
                    //网络结果返回，先更新UI
                    mView.showCollected(add, true);
                    //将返回结果同步到数据库
                    mModel.changeCollectedState(comicDetailBean, add);
                });
    }

    public void updateCollectedComicData(ComicDetailBean comicDetailBean) {
        if (mModel == null) return;
        mModel.updateCollectedComicData(comicDetailBean);
    }

    @Override
    public void detachVM() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.detachVM();
    }
}
