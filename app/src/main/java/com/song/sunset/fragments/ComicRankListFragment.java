package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.song.sunset.activitys.ComicListActivity;
import com.song.sunset.adapters.ComicListAdapter;
import com.song.sunset.beans.ComicListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.fragments.base.LoadableListFragment;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.U17ComicApi;

import java.util.List;

import rx.Observable;

/**
 * Created by songmw3 on 2017/3/3.
 * E-mail:z53520@qq.com
 */
public class ComicRankListFragment extends LoadableListFragment<ComicListAdapter, ComicListBean> {

    private String argName = "";
    private int argValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            argName = bundle.getString(ComicListActivity.ARG_NAME);
            argValue = bundle.getInt(ComicListActivity.ARG_VALUE);
        } else {//每日更新的标识
            argName = "sort";
            argValue = 0;
        }
    }

    @Override
    protected Observable<BaseBean<ComicListBean>> getRetrofitApi(int page) {
        return RetrofitService.createApi(U17ComicApi.class).queryComicListRDByGetObservable(page, argName, argValue);
    }

    @Override
    protected List<?> getListData(ComicListBean comicListBean) {
        if (comicListBean != null) return comicListBean.getComics();
        else return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        };
    }

    @Override
    public ComicListAdapter setAdapter() {
        return new ComicListAdapter(getActivity());
    }
}
