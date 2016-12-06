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

import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicListBean;
import com.song.sunset.impls.OnRVItemClickListener;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.R;
import com.song.sunset.activitys.ComicListActivity;
import com.song.sunset.adapters.ComicListAdapter;
import com.song.sunset.impls.LoadingMoreListener;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCall;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitApiBuilder;
import com.song.sunset.utils.service.RetrofitApi;
import com.song.sunset.views.LoadMoreRecyclerView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Song on 2016/8/27 0027.
 * Email:z53520@qq.com
 */
public class ComicListFragment extends BaseFragment implements LoadingMoreListener, SwipeRefreshLayout.OnRefreshListener, OnRVItemClickListener {

    private boolean isVisiable = false;
    private boolean isLoading = false;
    private boolean isRefreshing = false;
    private boolean hasCache = false;
    private ProgressLayout progressLayout;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RelativeLayout progressBar;
    private ComicListAdapter adapter;
    private int currentPage = 1;
    private String argName = "";
    private int argValue;

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
//        getDataFromRetrofit2(currentPage);
    }

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
        return inflater.inflate(R.layout.fragment_comiclist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress);
        progressLayout.showLoading();

        progressBar = (RelativeLayout) view.findViewById(R.id.id_loading_more_progress);
        showProgress(false);

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
        adapter.setOnRVItemClickListener(this);

        //        getDataFromNet(currentPage);
        getDataFromRetrofit2(currentPage);
    }

    @Override
    public void onClick(View v, int position) {
//        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
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
//        getDataFromNet(1);
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
//        getDataFromNet(currentPage);
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
//        Fresco.getImagePipeline().clearCaches();
    }

    public void getDataFromRetrofit2(int page) {
        Call<BaseBean<ComicListBean>> call = RetrofitApiBuilder.getRetrofitApi(RetrofitApi.class).queryComicListRDByGetCall(page, argName, argValue);
        RetrofitCall.call(call, new RetrofitCallback<ComicListBean>() {
            @Override
            public void onSuccess(ComicListBean comicListBean) {
                progressLayout.showContent();
                List<ComicListBean.ComicsBean> comicsBeanList = comicListBean.getComics();
                if (isRefreshing) {
                    currentPage = 1;
                    isRefreshing = false;
                    adapter.setData(comicsBeanList);
                    refreshLayout.setRefreshing(false);
                } else {
                    if (isLoading) {
                        isLoading = false;
                    }
                    adapter.addDatas(comicsBeanList);
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                if (isRefreshing) {
                    isRefreshing = false;
                    refreshLayout.setRefreshing(false);
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

//    public void getDataFromNet(int page) {
//        hasCache = false;
//        RequestQueue queue = SampleVolleyFactory.getRequestQueue(getActivity());
//        GsonRequest gsonRequest = new GsonRequest<>(AppServices.getComicListUrl(page, argValue, argName), ComicListRD.class,
//                new Response.Listener<ComicListRD>() {
//                    @Override
//                    public void onResponse(ComicListRD response) {
//                        hasCache = true;
//                        mLoadingAndRetryManager.showContent();
//                        if (response == null || response.getData() == null || response.getData().getStateCode() == 0) {
//                            showProgress(false);
//                            return;
//                        }
//                        List<ComicListRD.DataBean.ReturnDataBean.ComicsBean> comicsBeanList = response.getData().getReturnData().getComics();
//                        if (isRefreshing) {
//                            currentPage = 1;
//                            isRefreshing = false;
//                            adapter.setData(comicsBeanList);
//                            refreshLayout.setRefreshing(false);
//                        } else {
//                            if (isLoading) {
//                                isLoading = false;
//                            }
//                            adapter.addDatas(comicsBeanList);
//                            showProgress(false);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        if (isRefreshing) {
//                            isRefreshing = false;
//                            refreshLayout.setRefreshing(false);
//                        } else {
//                            currentPage--;
//                            if (isLoading) {
//                                isLoading = false;
//                                showProgress(false);
//                            } else {
//                                if (hasCache)
//                                    mLoadingAndRetryManager.showContent();
//                                else
//                                    mLoadingAndRetryManager.showRetry();
//                            }
//                        }
//                    }
//                });
//        gsonRequest.setRetryPolicy(new DefaultRetryPolicy());
//        queue.add(gsonRequest);
//    }
}
