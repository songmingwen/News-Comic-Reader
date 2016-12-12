package com.song.sunset.model;

import com.song.sunset.beans.ComicListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.impls.ComicListView;
import com.song.sunset.utils.retrofit.RetrofitCall;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.ComicApi;

import retrofit2.Call;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 */
public class ComicListModel {

    private int nextShowPage = 1;
    private boolean isLoading = false;
    private boolean isRefreshing = false;

    public void loadingMoreData(String argName, int argValue, final ComicListView comicListView) {
        if (isLoading) {
            return;
        }
        int page = nextShowPage;
        isLoading = true;
        if (page == 1) {
            comicListView.showLoading();
        }
        comicListView.showLoadingMoreProgress();
        Call<BaseBean<ComicListBean>> call = RetrofitService.createApi(ComicApi.class).queryComicListRDByGetCall(page, argName, argValue);
        RetrofitCall.call(call, new RetrofitCallback<ComicListBean>() {
            @Override
            public void onSuccess(ComicListBean comicListBean) {
                comicListView.showContent(comicListBean.getComics(), false);
                comicListView.hideLoadingMoreProgress();
                nextShowPage++;
                isLoading = false;
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                comicListView.hideLoadingMoreProgress();
                if (nextShowPage == 1) {
                    comicListView.showError();
                }
                isLoading = false;
            }
        });
    }

    public void refreshingData(String argName, int argValue, final ComicListView comicListView) {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        Call<BaseBean<ComicListBean>> call = RetrofitService.createApi(ComicApi.class).queryComicListRDByGetCall(1, argName, argValue);
        RetrofitCall.call(call, new RetrofitCallback<ComicListBean>() {
            @Override
            public void onSuccess(ComicListBean comicListBean) {
                comicListView.showContent(comicListBean.getComics(), true);
                comicListView.hideRefreshLayout();
                nextShowPage = 2;
                isRefreshing = false;
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                comicListView.hideRefreshLayout();
                isRefreshing = false;
            }
        });
    }
}
