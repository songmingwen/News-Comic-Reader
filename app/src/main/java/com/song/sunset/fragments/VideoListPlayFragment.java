package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.song.sunset.R;
import com.song.sunset.adapters.VideoListAdapter;
import com.song.sunset.beans.VideoBean;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.impls.LoadingMoreListener;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.api.PhoenixNewsApi;
import com.song.sunset.utils.api.WholeApi;
import com.song.sunset.views.LoadMoreRecyclerView;
import com.song.video.SimplePlayerLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import rx.Observable;

/**
 * Created by Song on 2017/4/27 0027.
 * E-mail: z53520@qq.com
 */

public class VideoListPlayFragment extends BaseFragment implements PtrHandler, LoadingMoreListener, LoadMoreRecyclerView.VideoListPlayListener {

    private String typeid = "";
    private LoadMoreRecyclerView recyclerView;
    private VideoListAdapter mAdapter;
    private int currentPage = 1;
    private boolean isLoading, isRefreshing = false, first;
    private PtrFrameLayout refreshLayout;
    private ProgressLayout progressLayout;
    private RelativeLayout progressBar;

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDataFromRetrofit2(1);
        }
    };

    private SimplePlayerLayout mPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        first = true;
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            typeid = bundle.getString("typeid");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videolist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress__);
        progressLayout.showLoading();

        mPlayer = (SimplePlayerLayout) view.findViewById(R.id.video_in_list);
        mPlayer.setOnScrollListener(false);
        mPlayer.setCanChangeOrientation(false);

        progressBar = (RelativeLayout) view.findViewById(R.id.id_loading_more_progress);
        showProgress(false);

        refreshLayout = (PtrFrameLayout) view.findViewById(R.id.id_video_list_swipe_refresh);
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
        header.initWithString("Song");
        header.setTextColor(getResources().getColor(R.color.colorPrimary));
        header.setBackgroundColor(getResources().getColor(R.color.white));

        refreshLayout.setDurationToCloseHeader(1000);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.setPtrHandler(this);

        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.rv_video_list);

        mAdapter = new VideoListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLoadingMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth() / 3;
            }
        });
        getDataFromRetrofit2(currentPage);

        setOnScrollListener();

    }

    private void setOnScrollListener() {
        recyclerView.setVideoListener(this);
    }

    @Override
    public void onResume() {
        if (mPlayer != null) {
            mPlayer.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mPlayer != null) {
            mPlayer.onPause();
        }
        super.onPause();
    }

    public void getDataFromRetrofit2(int page) {
        Observable<List<VideoBean>> observable = RetrofitService.createApi(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL).queryVideoObservable(page, "list", typeid);
        RxUtil.phoenixNewsSubscribe(observable, new RetrofitCallback<VideoBean>() {
            @Override
            public void onSuccess(VideoBean videoBean) {
                progressLayout.showContent();
                List<VideoBean.ItemBean> videoBeanList = videoBean.getItem();
                if (isRefreshing) {
                    currentPage = 1;
                    isRefreshing = false;
                    if (mAdapter != null) {
                        mAdapter.setData(videoBeanList);
                    }
                    refreshLayout.refreshComplete();
                } else {
                    if (isLoading) {
                        isLoading = false;
                    }
                    if (mAdapter != null) {
                        mAdapter.addDatas(videoBeanList);
                        showProgress(false);
                    }
                }
                if (first) {
                    first = false;
                    recyclerView.smoothScrollBy(0, 5);
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
    public void onStop() {
        super.onStop();
        currentPage = 1;
        isLoading = false;
        isRefreshing = false;
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLoadingMore() {
        if (isLoading) {
            return;
        }
        showProgress(true);
        currentPage++;
        isLoading = true;
        getDataFromRetrofit2(currentPage);
    }

    public void showProgress(boolean flag) {
        progressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void playVideo(int position) {
        mPlayer.setVisibility(View.VISIBLE);
        VideoBean.ItemBean mItemBean = mAdapter.getData().get(position);
        mPlayer.setCover(mItemBean.getImage());
        mPlayer.setTitle(mItemBean.getTitle());
        mPlayer.play(mItemBean.getVideo_url());
    }

    @Override
    public void stopVideo() {
        mPlayer.onDestroy();
        mPlayer.setVisibility(View.GONE);
    }

    @Override
    public void locate(float y) {
        int dy = ViewUtil.getStatusBarHeight() + ViewUtil.dip2px(44) - ViewUtil.dip2px(50);
        mPlayer.setY(y - dy);
    }
}