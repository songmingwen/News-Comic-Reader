package com.song.sunset.presenter;

import com.song.core.base.CoreBasePresenter;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.model.ComicDetailModel;
import com.song.sunset.utils.retrofit.ObservableTool;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.view.ComicDetailView;

/**
 * Created by songmw3 on 2016/12/8.
 *
 */
public class ComicDetailPresenter extends CoreBasePresenter<ComicDetailModel, ComicDetailView> {

    @Override
    public void onStart() {
    }

    public void showData(int comicId) {
        ObservableTool.comicSubscribe(mModel.getData(comicId), new RetrofitCallback<ComicDetailBean>() {
            @Override
            public void onSuccess(ComicDetailBean comicDetailBean) {
                mView.setData(comicDetailBean);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }

    public void showCollecteState(int comicId) {
        mView.showCollected(mModel.getCollectedState(comicId), false);
    }

    public void changeCollectedState(int comicId, ComicDetailBean comicDetailBean) {
        mView.showCollected(mModel.changeCollectedState(comicId, comicDetailBean), true);
    }
}
