package com.song.sunset.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.song.sunset.R;
import com.song.sunset.activitys.VideoListActivity;
import com.song.sunset.adapters.VideoListHelperAdapter;
import com.song.sunset.phoenix.bean.VideoDetailBean;
import com.song.sunset.phoenix.bean.VideoListsBean;
import com.song.sunset.base.fragment.BaseFragment;
import com.song.sunset.base.AppConfig;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.net.Net;
import com.song.sunset.base.rxjava.RxUtil;
import com.song.sunset.phoenix.api.PhoenixNewsApi;
import com.song.sunset.base.api.WholeApi;
import com.song.sunset.widget.BaseLoadMoreView;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import io.reactivex.Observable;

/**
 * Created by Song on 2017/3/13.
 * E-mail:z53520@qq.com
 */

public class VideoListHelperFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, PtrHandler {

    private String typeId = "";
    private RecyclerView recyclerView;
    private VideoListHelperAdapter mAdapter;
    private int currentPage = 1;
    private boolean isLoading, isRefreshing = false;
    private PtrFrameLayout refreshLayout;
    private ProgressLayout progressLayout;

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDataFromRetrofit2(1);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            typeId = bundle.getString(VideoListActivity.TYPE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videolist_helper_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressLayout = view.findViewById(R.id.progress);
        progressLayout.showLoading();

        refreshLayout = view.findViewById(R.id.id_comic_list_swipe_refresh);
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
        header.initWithString("Song");
        header.setTextColor(getResources().getColor(R.color.colorPrimary));
        header.setBackgroundColor(getResources().getColor(R.color.white));

        refreshLayout.setDurationToCloseHeader(AppConfig.REFRESH_CLOSE_TIME);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.setPtrHandler(this);

        recyclerView = view.findViewById(R.id.rv_video_list);
        mAdapter = new VideoListHelperAdapter(getActivity());
        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setLoadMoreView(new BaseLoadMoreView());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth() / 3;
            }
        });
        getDataFromRetrofit2(currentPage);
    }

    public void getDataFromRetrofit2(int page) {
        Observable<List<VideoListsBean>> observable = Net.createService(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL).queryVideoObservable(page, "list", typeId);
        RxUtil.phoenixNewsSubscribe(observable, new RetrofitCallback<VideoListsBean>() {
            @Override
            public void onSuccess(VideoListsBean videoBean) {
                progressLayout.showContent();
                List<VideoDetailBean> videoBeanList = videoBean.getItem();
                if (isRefreshing) {
                    currentPage = 1;
                    isRefreshing = false;
                    if (mAdapter != null) {
                        mAdapter.setNewData(videoBeanList);
                    }
                    refreshLayout.refreshComplete();
                } else {
                    if (isLoading) {
                        isLoading = false;
                    }
                    if (mAdapter != null) {
                        mAdapter.addData(videoBeanList);
                        mAdapter.loadMoreComplete();
                    }
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
                        mAdapter.loadMoreEnd();
                    } else {
                        progressLayout.showError(getResources().getDrawable(R.drawable.icon_new_style_failure), "连接失败",
                                "无法建立连接",
                                "点击重试", errorClickListener, null);
                    }
                }
            }
        });
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
        getDataFromRetrofit2(1);
    }

    @Override
    public void onLoadMoreRequested() {
        if (isLoading) {
            return;
        }
        currentPage++;
        isLoading = true;
        getDataFromRetrofit2(currentPage);
    }

    @Override
    public void onStop() {
        super.onStop();
        currentPage = 1;
        isLoading = false;
        isRefreshing = false;
    }
}
