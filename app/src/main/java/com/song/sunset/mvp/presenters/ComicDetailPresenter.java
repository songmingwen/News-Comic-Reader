package com.song.sunset.mvp.presenters;

import com.song.core.base.CoreBasePresenter;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.mvp.models.ComicDetailModel;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.mvp.views.ComicDetailView;

/**
 * Created by Song on 2016/12/8.
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

    public void showCollectedStated(int comicId) {
        if (mView == null || mModel == null) return;
        mView.showCollected(mModel.isCollected(comicId), false);
    }

    public void changeCollectedState(final ComicDetailBean comicDetailBean) {
        if (mView == null || mModel == null) return;
        mModel.changeCollectedStateFromNet(comicDetailBean,
                new ComicDetailModel.ChangeCollectionListener() {
                    @Override
                    public void collected(boolean add) {
                        //网络结果返回，先更新UI
                        mView.showCollected(add, true);
                        //将返回结果同步到数据库
                        mModel.changeCollectedState(comicDetailBean, add);
                    }
                });
    }

    public void updateCollectedComicData(ComicDetailBean comicDetailBean) {
        if (mModel == null) return;
        mModel.updateCollectedComicData(comicDetailBean);
    }
}
