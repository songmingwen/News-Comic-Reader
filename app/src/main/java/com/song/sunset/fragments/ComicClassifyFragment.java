package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicClassifyBean;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicClassifyAdapter;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.U17ComicApi;

import rx.Observable;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
public class ComicClassifyFragment extends Fragment {
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private RecyclerView recyclerView;
    private ComicClassifyAdapter adapter;
    private boolean hasCache = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comicclassify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        getDataFromNet();
                        getDataFromRetrofit2();
                    }
                });
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.id_comic_classify_recycler);
        adapter = new ComicClassifyAdapter(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        };
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == ComicClassifyAdapter.TOP_TYPE) {
                    return 2;
                } else if (itemViewType == ComicClassifyAdapter.BOTTOM_TYPE) {
                    return 2;
                } else {
                    return 6;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
//        getDataFromNet();
        getDataFromRetrofit2();
    }

//    public void getDataFromNet() {
//        mLoadingAndRetryManager.showLoading();
//        hasCache = false;
//        RequestQueue queue = SampleVolleyFactory.getRequestQueue(getActivity());
//        GsonRequest gsonRequest = new GsonRequest<>(AppServices.COMIC_CLASSIFY_URL, ComicClassifyRD.class,
//                new Response.Listener<ComicClassifyRD>() {
//                    @Override
//                    public void onResponse(ComicClassifyRD response) {
//                        hasCache = true;
//                        mLoadingAndRetryManager.showContent();
//                        ComicClassifyRD.DataBean.ReturnDataBean returnData = response.getData().getReturnData();
//                        adapter.setData(returnData);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        if (hasCache)
//                            mLoadingAndRetryManager.showContent();
//                        else
//                            mLoadingAndRetryManager.showRetry();
//                    }
//                });
//        gsonRequest.setRetryPolicy(new DefaultRetryPolicy());
//        queue.add(gsonRequest);
//    }

    public void getDataFromRetrofit2() {
        mLoadingAndRetryManager.showLoading();
        Observable<BaseBean<ComicClassifyBean>> observable = RetrofitService.createApi(U17ComicApi.class).queryComicClassifyBeanByGetObservable(2);
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicClassifyBean>() {
            @Override
            public void onSuccess(ComicClassifyBean comicReadBean) {
                mLoadingAndRetryManager.showContent();
                adapter.setData(comicReadBean);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                mLoadingAndRetryManager.showRetry();
            }
        });
    }
}
