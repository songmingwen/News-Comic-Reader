package com.song.sunset.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.adapters.base.BaseRVAdapterWithoutVH;
import com.song.sunset.interfaces.EndlessRecyclerOnScrollListener;
import com.song.sunset.mvp.views.ListCallView;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.widget.LoadingFooter;
import com.song.sunset.beans.basebeans.PageEntity;
import com.song.sunset.R;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.mvp.presenters.ListPresenter;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.widget.RecyclerViewWithHF;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public abstract class RVLoadableFragment<Adapter extends BaseRVAdapterWithoutVH, Bean extends PageEntity>
        extends BaseFragment implements PtrHandler, RetrofitCallback<Bean>, ListCallView {

    protected Adapter mInnerAdapter;
    protected ProgressLayout mProgressLayout;
    protected PtrFrameLayout mPtrFrameLayout;
    protected RecyclerView mRecyclerView;
    protected ListPresenter mPresenter;
    private LoadingFooter mFooterView;

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.firstLoading();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ListPresenter(this);
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

        mPtrFrameLayout = getPtrLayout(view);
        if (mPtrFrameLayout != null) {
            StoreHouseHeader header = new StoreHouseHeader(getContext());
            header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
            header.initWithString("Song");
            header.setTextColor(getResources().getColor(R.color.colorPrimary));
            header.setBackgroundColor(getResources().getColor(R.color.white));

//            MaterialHeader header = new MaterialHeader(getActivity());
//            PtrClassicDefaultHeader header =  new PtrClassicDefaultHeader(getActivity());
//            SampleHeader header =  new SampleHeader(getActivity());

            mPtrFrameLayout.setDurationToCloseHeader(AppConfig.REFRESH_CLOSE_TIME);
            mPtrFrameLayout.setHeaderView(header);
            mPtrFrameLayout.addPtrUIHandler(header);
            mPtrFrameLayout.setPtrHandler(this);
        }

        mInnerAdapter = getAdapter();
        mRecyclerView.setAdapter(mInnerAdapter);

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        //添加分割线，无默认分割线
        RecyclerView.ItemDecoration itemDecoration = getRecyclerViewDecoration();
        if (itemDecoration != null) {
            mRecyclerView.addItemDecoration(itemDecoration);
        }
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mPresenter.firstLoading();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (mPresenter != null) {
            mPresenter.startRefresh();
        }
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            loadMoreSafely();
        }
    };

    @Override
    public void onSuccess(Bean bean) {
        if (mPresenter != null) {
            mPresenter.setData(bean);
        }
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        if (mPresenter != null) {
            mPresenter.dealWithError();
        }
    }

    @Override
    public void firstLoading() {
        if (mProgressLayout != null) {
            mProgressLayout.showLoading();
        }
        loadFirstPage();
    }

    @Override
    public void refresh() {
        refreshMore();
    }

    @Override
    public void loadMorePage(int currentPage) {
        loadMore(currentPage);
    }

    @Override
    public void showContent() {
        if (mProgressLayout != null) {
            mProgressLayout.showContent();
        }
    }

    @Override
    public void showError() {
        if (mProgressLayout != null) {
            mProgressLayout.showError(getResources().getDrawable(R.drawable.icon_new_style_failure), "连接失败",
                    "无法建立连接", "点击重试", errorClickListener, null);
        }
    }

    @Override
    public <BeanData extends PageEntity> void addData(boolean atTop, BeanData bean) {
        if (mInnerAdapter != null) {
            if (atTop) {
                mInnerAdapter.setData(bean.getData());
            } else {
                mInnerAdapter.addDataAtBottom(bean.getData());
            }
        }
    }

    @Override
    public void refreshComplete() {
        if (mPtrFrameLayout != null) {
            mPtrFrameLayout.refreshComplete();
        }
    }

    @Override
    public void changeFooterState(LoadingFooter.State state) {
        View.OnClickListener onClickListener = null;
        if (state == LoadingFooter.State.NetWorkError) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadMoreSafely();
                }
            };
        }
        //只有RecyclerViewWithHF 才有添加foot，head的功能
        if (mRecyclerView instanceof RecyclerViewWithHF) {
            RecyclerViewWithHF recyclerView = (RecyclerViewWithHF) mRecyclerView;
            if (mFooterView == null) {
                mFooterView = new LoadingFooter(getActivity());
                recyclerView.addFooterView(mFooterView);
            }
            mFooterView.setOnClickListener(onClickListener);
            mFooterView.setState(state);
        }
    }

    private void loadMoreSafely() {
        if (mPresenter != null) {
            mPresenter.loadMore();
        }
    }

    protected abstract void loadFirstPage();

    protected abstract void refreshMore();

    protected abstract void loadMore(int pageNum);

    protected abstract ProgressLayout getWrapper(View rootView);

    protected abstract PtrFrameLayout getPtrLayout(View rootView);

    protected abstract Adapter getAdapter();

    public abstract int getLayout();

    public abstract RecyclerView getRecyclerView(View rootView);

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    public RecyclerView.ItemDecoration getRecyclerViewDecoration() {
        return null;
    }
}
