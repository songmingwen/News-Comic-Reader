package com.song.sunset.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.core.recyclerviewwithheadandfoot.EndlessRecyclerOnScrollListener;
import com.song.core.recyclerviewwithheadandfoot.HeaderAndFooterRecyclerViewAdapter;
import com.song.core.recyclerviewwithheadandfoot.HeaderSpanSizeLookup;
import com.song.core.recyclerviewwithheadandfoot.RecyclerViewStateUtils;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.widget.LoadingFooter;
import com.song.sunset.R;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.basebeans.PageEntity;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;


import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 */
public abstract class LoadableFragment<Adapter extends BaseRecyclerViewAdapter
        , Bean extends PageEntity> extends BaseFragment implements PtrHandler, RetrofitCallback<Bean> {

    /**
     * 服务器端一共多少条数据
     */
    private int TOTAL_COUNTER = 64;

    /**
     * 已经获取到多少条数据了
     */
    private int currentPage = 1;

    private boolean isLoading = false;
    private boolean isRefreshing = false;
    private boolean isFirstLoading = false;
    private Adapter mInnerAdapter;
    private ProgressLayout mProgressLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView mRecyclerView;

    private void refreshComplete() {
        if (mPtrFrameLayout != null) {
            mPtrFrameLayout.refreshComplete();
        }
    }

    private void showContent() {
        if (mProgressLayout != null) {
            mProgressLayout.showContent();
        }
    }

    private void showLoading() {
        if (mProgressLayout != null) {
            mProgressLayout.showLoading();
        }
    }


    private void showError() {
        if (mProgressLayout != null) {
            mProgressLayout.showError(getResources().getDrawable(R.drawable.icon_new_style_failure), "连接失败",
                    "无法建立连接",
                    "点击重试", errorClickListener, null);
        }
    }

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoading();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mProgressLayout = getWrapper(view);
        mRecyclerView = getRecyclerView(view);
        if (mRecyclerView == null || mProgressLayout == null) {
            return;
        }

        showLoading();

        mPtrFrameLayout = getPtrLayout(view);
        if (mPtrFrameLayout != null) {
            StoreHouseHeader header = new StoreHouseHeader(getContext());
            header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
            header.initWithString("Song");
            header.setTextColor(getResources().getColor(R.color.colorPrimary));
            header.setBackgroundColor(getResources().getColor(R.color.white));

            mPtrFrameLayout.setDurationToCloseHeader(AppConfig.REFRESH_CLOSE_TIME);
            mPtrFrameLayout.setHeaderView(header);
            mPtrFrameLayout.addPtrUIHandler(header);
            mPtrFrameLayout.setPtrHandler(this);
        }


        mInnerAdapter = getAdapter();
        HeaderAndFooterRecyclerViewAdapter outAdapter = new HeaderAndFooterRecyclerViewAdapter(mInnerAdapter);
        mRecyclerView.setAdapter(outAdapter);

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter();
            manager.setSpanSizeLookup(new HeaderSpanSizeLookup(adapter, manager.getSpanCount()));
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        loadMore(1);
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
        loadMore(1);
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (isLoading) {
                return;
            }
            if (currentPage < TOTAL_COUNTER) {
                startLoading();
            } else {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    @Override
    public void onSuccess(Bean bean) {
        TOTAL_COUNTER = bean.getPageSum();
        if (isFirstLoading) {
            isFirstLoading = false;
        }
        showContent();
        if (isRefreshing) {
            isRefreshing = false;
            if (mInnerAdapter != null) {
                mInnerAdapter.setData(bean.getData());
            }
            refreshComplete();
        } else {
            if (isLoading) {
                isLoading = false;
            }
            if (mInnerAdapter != null) {
                mInnerAdapter.addDataAtBottom(bean.getData());
            }
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, LoadingFooter.State.Normal, null);
        }
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        if (isRefreshing) {
            isRefreshing = false;
            refreshComplete();
        } else {
            currentPage--;
            if (isLoading) {
                isLoading = false;
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView,
                        LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isLoading) {
                                    return;
                                }
                                startLoading();
                            }
                        });
            } else {
                showError();
            }
        }
    }

    private void startLoading() {
        isLoading = true;
        currentPage++;
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, LoadingFooter.State.Loading, null);
        loadMore(currentPage);
    }

    protected abstract void loadMore(int pageNum);

    protected abstract ProgressLayout getWrapper(View rootView);

    protected abstract PtrFrameLayout getPtrLayout(View rootView);

    protected abstract Adapter getAdapter();

    public abstract int getLayout();

    public abstract RecyclerView getRecyclerView(View rootView);

    protected abstract RecyclerView.LayoutManager getLayoutManager();
}
