package com.song.sunset.comic.mvp.models;

import com.song.sunset.comic.bean.ComicListBean;
import com.song.sunset.base.bean.BaseBean;
import com.song.sunset.base.net.RetrofitCall;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.net.Net;
import com.song.sunset.comic.api.U17ComicApi;
import com.song.sunset.comic.mvp.views.ComicListView;

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
        Call<BaseBean<ComicListBean>> call = Net.INSTANCE.createService(U17ComicApi.class).queryComicListRDByCall(page, argName, argValue);
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
        Call<BaseBean<ComicListBean>> call = Net.INSTANCE.createService(U17ComicApi.class).queryComicListRDByCall(1, argName, argValue);
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
