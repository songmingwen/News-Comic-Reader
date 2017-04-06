package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.song.sunset.R;
import com.song.sunset.adapters.IfengListAdapter;
import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.beans.IfengNewsListBean;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.impls.LoadingMoreListener;
import com.song.sunset.utils.RecyclerViewDivider;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.service.IfengNewsApi;
import com.song.sunset.utils.service.WholeApi;
import com.song.sunset.views.LoadMoreRecyclerView;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import rx.Observable;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */

public class IfengListFragment extends BaseFragment implements RetrofitCallback<IfengNewsListBean>, PtrHandler, LoadingMoreListener {

    private RelativeLayout progressBar;
    private LoadMoreRecyclerView recyclerView;
    private IfengListAdapter mAdapter;
    private PtrFrameLayout refreshLayout;
    private ProgressLayout progressLayout;
    private boolean isLoading, isRefreshing = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ifeng_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress);
        progressLayout.showLoading();

        progressBar = (RelativeLayout) view.findViewById(R.id.id_loading_more_progress);
        showProgress(false);

        refreshLayout = (PtrFrameLayout) view.findViewById(R.id.id_ifeng_list_swipe_refresh);
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
        header.initWithString("Song");
        header.setTextColor(getResources().getColor(R.color.colorPrimary));
        header.setBackgroundColor(getResources().getColor(R.color.white));

        refreshLayout.setDurationToCloseHeader(1500);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.setPtrHandler(this);

        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.id_recyclerview_ifeng_list);
        mAdapter = new IfengListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLoadingMoreListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth() / 3;
            }
        });
        //添加分割线
//        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        getDataFromRetrofit2(IfengNewsApi.DOWN);
    }

    private void getDataFromRetrofit2(String action) {
        Observable<List<IfengNewsListBean>> observable = RetrofitService
                .createApi(IfengNewsApi.class, WholeApi.IFENG_NEWS_BASE_URL)
                .queryIfengListObservable(action);
        RxUtil.ifengNewsSubscribe(observable, this);
    }

    @Override
    public void onSuccess(IfengNewsListBean ifengNewsListBean) {
        progressLayout.showContent();
        List<IfengChannelBean> ifengChannelBeanList = ifengNewsListBean.getItem();
        if (isRefreshing) {
            isRefreshing = false;
            mAdapter.addTopData(ifengChannelBeanList);
            refreshLayout.refreshComplete();
        } else {
            if (isLoading) {
                isLoading = false;
            }
            mAdapter.addBottomData(ifengChannelBeanList);
            showProgress(false);
        }
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        if (isRefreshing) {
            isRefreshing = false;
            refreshLayout.refreshComplete();
        } else {
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

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressLayout.showLoading();
            getDataFromRetrofit2(IfengNewsApi.DOWN);
        }
    };

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
        getDataFromRetrofit2(IfengNewsApi.DOWN);
    }

    @Override
    public void onLoadingMore() {
        if (isLoading) {
            return;
        }
        showProgress(true);
        isLoading = true;
        getDataFromRetrofit2(IfengNewsApi.UP);
    }

    public void showProgress(boolean flag) {
        progressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
}
