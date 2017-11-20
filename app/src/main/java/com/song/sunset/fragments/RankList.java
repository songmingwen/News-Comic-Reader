package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.song.sunset.R;
import com.song.sunset.activitys.ComicListActivity;
import com.song.sunset.adapters.ComicListAdapter;
import com.song.sunset.beans.ComicListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.fragments.base.RVLoadableFragment;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.rxjava.RxUtil;

import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Observable;

/**
 * Created by Song on 2017/11/13 0013.
 * E-mail: z53520@qq.com
 */

public class RankList extends RVLoadableFragment<ComicListAdapter, ComicListBean> {

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        RecyclerViewUtils.setHeaderView(mRecyclerView, new SampleHeader(getActivity()));
    }

    @Override
    protected ProgressLayout getWrapper(View rootView) {
        return (ProgressLayout) rootView.findViewById(R.id.id_loadable_fragment_progress);
    }

    @Override
    protected PtrFrameLayout getPtrLayout(View rootView) {
        return (PtrFrameLayout) rootView.findViewById(R.id.id_loadable_fragment_list_swipe_refresh);
    }

    @Override
    protected ComicListAdapter getAdapter() {
        return new ComicListAdapter(getActivity());
    }

    @Override
    protected void refreshMore() {
        loadData(1, argName, argValue);
    }

    @Override
    protected void loadMore(int pageNum) {
        loadData(pageNum, argName, argValue);
    }

    private void loadData(int page, String argName, int argValue) {
        Observable<BaseBean<ComicListBean>> observable = RetrofitService.createApi(U17ComicApi.class).queryComicListRDByGetObservable(page, argName, argValue);
        RxUtil.comicSubscribe(observable, RankList.this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_loadable_list;
    }

    @Override
    public RecyclerView getRecyclerView(View rootView) {
        return (RecyclerView) rootView.findViewById(R.id.id_recyclerview_loadable_fragment);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        };
    }
}
