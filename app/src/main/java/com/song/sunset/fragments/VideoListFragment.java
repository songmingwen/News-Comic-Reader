package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.song.sunset.R;
import com.song.sunset.adapters.VideoListAdapter;
import com.song.sunset.beans.VideoBean;
import com.song.sunset.impls.LoadingMoreListener;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.VideoApi;
import com.song.sunset.utils.service.WholeApi;
import com.song.sunset.views.LoadMoreRecyclerView;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import rx.Observable;

/**
 * Created by songmw3 on 2016/12/21.
 */
public class VideoListFragment extends BaseFragment implements LoadingMoreListener, SwipeRefreshLayout.OnRefreshListener, PtrHandler {
    private boolean isVisiable = false;
    private boolean isLoading = false;
    private boolean isRefreshing = false;
    private boolean hasCache = false;
    private ProgressLayout progressLayout;
    private LoadMoreRecyclerView recyclerView;
    //    private SwipeRefreshLayout refreshLayout;
    private PtrFrameLayout refreshLayout;
    private RelativeLayout progressBar;
    private VideoListAdapter adapter;
    private int currentPage = 1;
    private String name = "";
    private String typeid = "";
    private String chType = "";

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressLayout.showLoading();
            getDataFromRetrofit2(1);
        }
    };

    @Override
    protected void loadData() {
        super.loadData();
        isVisiable = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            chType = bundle.getString("chType");
            typeid = bundle.getString("typeid");
            name = bundle.getString("name");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videolist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress);
        progressLayout.showLoading();

        progressBar = (RelativeLayout) view.findViewById(R.id.id_loading_more_progress);
        showProgress(false);

        refreshLayout = (PtrFrameLayout) view.findViewById(R.id.id_comic_list_swipe_refresh);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setColorSchemeResources(R.color.color_2bbad8, R.color.color_ffa200);
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
        header.initWithString("Song");
        header.setTextColor(getResources().getColor(R.color.colorPrimary));
        header.setBackgroundColor(getResources().getColor(R.color.white));

        refreshLayout.setDurationToCloseHeader(1500);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.setPtrHandler(this);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();

            }
        }, 100);

        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.id_recyclerview_comiclist);
        adapter = new VideoListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth() / 3;
            }
        });
        recyclerView.setLoadingMoreListener(this);

        getDataFromRetrofit2(currentPage);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        getDataFromRetrofit2(1);
        isRefreshing = true;
    }

    @Override
    public void onLoadingMore() {
        if (isLoading) {
            return;
        }
        showProgress(true);
        currentPage++;
        getDataFromRetrofit2(currentPage);
        isLoading = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        currentPage = 1;
        isLoading = false;
        isRefreshing = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getDataFromRetrofit2(int page) {
        Observable<List<VideoBean>> observable = RetrofitService.createApi(VideoApi.class, WholeApi.VIDEO_BASE_URL).queryVideoRDByGetObservable(page, "list", typeid);
        RxUtil.videoSubscribe(observable, new RetrofitCallback<VideoBean>() {
            @Override
            public void onSuccess(VideoBean videoBean) {
                progressLayout.showContent();
                List<VideoBean.ItemBean> videoBeanList = videoBean.getItem();
                if (isRefreshing) {
                    currentPage = 1;
                    isRefreshing = false;
                    adapter.setData(videoBeanList);
                    refreshLayout.refreshComplete();
                } else {
                    if (isLoading) {
                        isLoading = false;
                    }
                    adapter.addDatas(videoBeanList);
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
                        progressLayout.showError(getResources().getDrawable(R.drawable.blackhole_broken), "连接失败",
                                "无法建立连接",
                                "点击重试", errorClickListener, null);
                    }
                }
            }
        });
    }

    public void showProgress(boolean flag) {
        progressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
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
        getDataFromRetrofit2(1);
        isRefreshing = true;
    }
}
