package com.song.sunset.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.song.sunset.R;
import com.song.sunset.adapters.VideoListAdapter;
import com.song.sunset.phoenix.bean.VideoDetailBean;
import com.song.sunset.phoenix.bean.VideoListsBean;
import com.song.sunset.base.fragment.BaseFragment;
import com.song.sunset.interfaces.LoadingMoreListener;
import com.song.sunset.base.AppConfig;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.widget.ProgressLayout;
import com.song.sunset.base.rxjava.RxUtil;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.net.Net;
import com.song.sunset.phoenix.api.PhoenixNewsApi;
import com.song.sunset.base.api.WholeApi;
import com.song.sunset.widget.LoadMoreRecyclerView;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import io.reactivex.Observable;

/**
 * Created by Song on 2016/12/21.
 */
public class VideoListFragment extends BaseFragment implements LoadingMoreListener, PtrHandler {
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
    protected void visible2User() {
        super.visible2User();
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
        progressLayout = view.findViewById(R.id.progress__);
        progressLayout.showLoading();

        progressBar = view.findViewById(R.id.id_loading_more_progress);
        showProgress(false);

        refreshLayout = view.findViewById(R.id.id_comic_list_swipe_refresh);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setColorSchemeResources(R.color.color_2bbad8, R.color.color_ffa200);
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, ViewUtil.dip2px(2), 0, ViewUtil.dip2px(2));
        header.initWithString("Song");
        header.setTextColor(getResources().getColor(R.color.colorPrimary));
        header.setBackgroundColor(getResources().getColor(R.color.white));

        refreshLayout.setDurationToCloseHeader(AppConfig.REFRESH_CLOSE_TIME);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.setPtrHandler(this);

        recyclerView = view.findViewById(R.id.id_recyclerview_comiclist);
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
    public void onLoadingMore() {
        if (isLoading) {
            return;
        }
        showProgress(true);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getDataFromRetrofit2(int page) {
        Observable<List<VideoListsBean>> observable = Net.INSTANCE.createService(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL).queryVideoObservable(page, "list", typeid);
        RxUtil.phoenixNewsSubscribe(observable, new RetrofitCallback<VideoListsBean>() {
            @Override
            public void onSuccess(VideoListsBean videoBean) {
                progressLayout.showContent();
                List<VideoDetailBean> videoBeanList = videoBean.getItem();
                if (isRefreshing) {
                    currentPage = 1;
                    isRefreshing = false;
                    adapter.setData(videoBeanList);
                    refreshLayout.refreshComplete();
                } else {
                    if (isLoading) {
                        isLoading = false;
                    }
                    adapter.addDataAtBottom(videoBeanList);
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
        isRefreshing = true;
        getDataFromRetrofit2(1);
    }
}
