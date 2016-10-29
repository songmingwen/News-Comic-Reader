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
import com.song.sunset.activitys.ComicListActivity;
import com.song.sunset.adapters.ComicListAdapter;
import com.song.sunset.beans.ComicListBean;
import com.song.sunset.impls.ComicListView;
import com.song.sunset.impls.LoadingMoreListener;
import com.song.sunset.presenter.ComicListPresenter;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.views.LoadMoreRecyclerView;

import java.util.List;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 */

public class MVPComicListFragment extends BaseFragment implements ComicListView, SwipeRefreshLayout.OnRefreshListener, LoadingMoreListener {

    private ComicListPresenter comicListPresenter;
    private ProgressLayout progressLayout;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RelativeLayout progressBar;
    private ComicListAdapter adapter;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        comicListPresenter = new ComicListPresenter(this);
        return inflater.inflate(R.layout.fragment_comiclist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress);

        progressBar = (RelativeLayout) view.findViewById(R.id.id_loading_more_progress);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_comic_list_swipe_refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.color_2bbad8, R.color.color_ffa200);

        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.id_recyclerview_comiclist);
        adapter = new ComicListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        });
        recyclerView.setLoadingMoreListener(this);
        comicListPresenter.loadingMoreData(argName, argValue);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        comicListPresenter = null;
    }

    @Override
    public void onRefresh() {
        comicListPresenter.refreshingData(argName, argValue);
    }

    @Override
    public void onLoadingMore() {
        comicListPresenter.loadingMoreData(argName, argValue);
    }

    @Override
    public void hideRefreshLayout() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadingMoreProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingMoreProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContent(List<ComicListBean.ComicsBean> list, boolean isRefresh) {
        if (isRefresh) {
            adapter.setData(list);
        } else {
            adapter.addDatas(list);
        }
        progressLayout.showContent();
    }

    @Override
    public void showLoading() {
        progressLayout.showLoading();
    }

    @Override
    public void showError() {
        progressLayout.showError(getResources().getDrawable(R.drawable.blackhole_broken), "连接失败",
                "无法建立连接",
                "点击重试", errorClickListener, null);
    }

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            comicListPresenter.loadingMoreData(argName, argValue);
        }
    };
}
