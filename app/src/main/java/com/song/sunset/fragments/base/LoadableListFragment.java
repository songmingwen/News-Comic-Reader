package com.song.sunset.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.song.sunset.R;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.adapters.base.DefaultLoadableAdapter;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.interfaces.LoadingMoreListener;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.widget.LoadMoreRecyclerView;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import rx.Observable;

/**
 * Created by Song on 2017/3/3.
 * E-mail:z53520@qq.com
 */

public abstract class LoadableListFragment<Adapter extends BaseRecyclerViewAdapter
        , Bean> extends BaseFragment implements PtrHandler, LoadingMoreListener {

    private boolean isLoading = false;
    private boolean isRefreshing = false;
    private ProgressLayout progressLayout;
    private LoadMoreRecyclerView recyclerView;
    private PtrFrameLayout refreshLayout;
    private RelativeLayout progressBar;
    private BaseRecyclerViewAdapter mAdapter;
    private int currentPage = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loadable_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressLayout = (ProgressLayout) view.findViewById(R.id.id_loadable_fragment_progress);
        progressLayout.showLoading();
        progressBar = (RelativeLayout) view.findViewById(R.id.id_loadable_fragment_loading_more_progress);
        showProgress(false);

        refreshLayout = (PtrFrameLayout) view.findViewById(R.id.id_loadable_fragment_list_swipe_refresh);
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
        header.initWithString("Song");
        header.setTextColor(getResources().getColor(R.color.colorPrimary));
        header.setBackgroundColor(getResources().getColor(R.color.white));

        refreshLayout.setDurationToCloseHeader(AppConfig.REFRESH_CLOSE_TIME);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.setPtrHandler(this);

        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.id_recyclerview_loadable_fragment);
        mAdapter = setAdapter();
        if (mAdapter == null) {
            mAdapter = new DefaultLoadableAdapter(getActivity());
        }
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLoadingMoreListener(this);

        getDataFromNet(currentPage);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        getDataFromNet(1);
    }

    public void showProgress(boolean flag) {
        progressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoadingMore() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        showProgress(true);
        currentPage++;
        getDataFromNet(currentPage);
    }

    @Override
    public void onStop() {
        super.onStop();
        currentPage = 1;
        isLoading = false;
        isRefreshing = false;
    }

    private void getDataFromNet(int page) {
        Observable<BaseBean<Bean>> observable = getRetrofitApi(page);
        RxUtil.comicSubscribe(observable, new RetrofitCallback<Bean>() {
            @Override
            public void onSuccess(Bean bean) {
                progressLayout.showContent();
                if (isRefreshing) {
                    currentPage = 1;
                    isRefreshing = false;
                    mAdapter.setData(getListData(bean));
                    refreshLayout.refreshComplete();
                } else {
                    if (isLoading) {
                        isLoading = false;
                    }
                    mAdapter.addDatas(getListData(bean));
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                if (isRefreshing) {
                    isRefreshing = false;
                    refreshLayout.refreshComplete();
                } else {
                    currentPage--;
                    if (isLoading) {
                        isLoading = false;
                        showProgress(false);
                    } else {
                        progressLayout.showError(getResources().getDrawable(R.drawable.icon_new_style_failure), "连接失败",
                                "无法建立连接",
                                "点击重试", errorClickListener, null);
                    }
                }
            }
        });
    }

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressLayout.showLoading();
            getDataFromNet(1);
        }
    };

    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 获取网络请求对象
     * eg:RetrofitService.createApi(ComicApi.class).queryComicListRDByGetObservable(page, argName, argValue);
     *
     * @return Observable 对象
     */
    protected abstract Observable<BaseBean<Bean>> getRetrofitApi(int page);

    public abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract List<?> getListData(Bean bean);

    public abstract Adapter setAdapter();
}
