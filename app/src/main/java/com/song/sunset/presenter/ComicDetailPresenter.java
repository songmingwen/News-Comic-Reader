package com.song.sunset.presenter;

import com.song.core.base.CoreBasePresenter;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.model.ComicDetailModel;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.view.ComicDetailView;

/**
 * Created by songmw3 on 2016/12/8.
 * E-mail:z53520@qq.com
 */
public class ComicDetailPresenter extends CoreBasePresenter<ComicDetailModel, ComicDetailView> {

    @Override
    public void onStart() {
    }

    public void showData(int comicId) {
        if (mModel == null) return;
        RxUtil.comicSubscribe(mModel.getData(comicId), new RetrofitCallback<ComicDetailBean>() {
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

    public void showCollecteState(int comicId) {
        if (mView == null || mModel == null) return;
        mView.showCollected(mModel.getCollectedState(comicId), false);
    }

    public void changeCollectedState(int comicId, ComicDetailBean comicDetailBean) {
        if (mView == null || mModel == null) return;
        mView.showCollected(mModel.changeCollectedState(comicId, comicDetailBean), true);
    }
}
