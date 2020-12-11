package com.song.sunset.mvp.models;

import com.song.sunset.beans.ComicListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.interfaces.ComicListView;
import com.song.sunset.utils.retrofit.RetrofitCall;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.Net;
import com.song.sunset.utils.api.U17ComicApi;

import retrofit2.Call;

/**
 * Created by Song on 2016/10/29 0029.
 * Email:z53520@qq.com
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
        Call<BaseBean<ComicListBean>> call = Net.createService(U17ComicApi.class).queryComicListRDByCall(page, argName, argValue);
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
        Call<BaseBean<ComicListBean>> call = Net.createService(U17ComicApi.class).queryComicListRDByCall(1, argName, argValue);
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
