package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicReadBean;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicReadAdapter;
import com.song.sunset.utils.retrofit.ObservableTool;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.ComicApi;
import com.song.sunset.views.ScaleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Song on 2016/8/30 0030.
 * Email:z53520@qq.com
 */
public class ComicReadActivity extends BaseActivity {

    public static final String OPEN_POSITION = "open_position";
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private String comicId = "";
    private int openPosition = -1;
    private ScaleRecyclerView recyclerView;
    private ComicReadAdapter adapter;
    private boolean hasCache = false;

    public static void start(Context context, String comicId, int openPosition) {
        Intent intent = new Intent(context, ComicReadActivity.class);
        intent.putExtra(ComicDetailActivity.COMIC_ID, comicId);
        intent.putExtra(OPEN_POSITION, openPosition);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_read);

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

        if (getIntent() != null) {
            comicId = getIntent().getStringExtra(ComicDetailActivity.COMIC_ID);
            openPosition = getIntent().getIntExtra(OPEN_POSITION, -1);
        }

        initView();
//        getDataFromNet();
        getDataFromRetrofit2();
    }

    private void initView() {
        recyclerView = (ScaleRecyclerView) findViewById(R.id.id_comic_read_recycler);
        adapter = new ComicReadAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(getLinearLayoutManager());
    }

    @NonNull
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        };
    }

    private int getRealPosition(List<ComicReadBean> returnData) {
        int realPosition = 0;
        for (int i = 0; i < openPosition; i++) {
            List<ComicReadBean.ImageListBean> image_list = returnData.get(i).getImage_list();
            for (ComicReadBean.ImageListBean images : image_list) {
                if (images == null || images.getImages() == null) {
                    continue;
                }
                realPosition += images.getImages().size();
            }
        }
        return realPosition;
    }

    @NonNull
    private ArrayList<ComicReadBean.ImageListBean.ImagesBean> getDataList(List<ComicReadBean> returnData) {
        ArrayList<ComicReadBean.ImageListBean.ImagesBean> dataList = new ArrayList<>();
        for (ComicReadBean data : returnData) {
            List<ComicReadBean.ImageListBean> image_list = data.getImage_list();
            for (ComicReadBean.ImageListBean image : image_list) {
                List<ComicReadBean.ImageListBean.ImagesBean> images = image.getImages();
                if (images == null) {
                    continue;
                }
                for (ComicReadBean.ImageListBean.ImagesBean imagesBean : images) {
                    dataList.add(imagesBean);
                }
            }
        }
        return dataList;
    }

    public void getDataFromRetrofit2() {
        mLoadingAndRetryManager.showLoading();
        Observable<BaseBean<List<ComicReadBean>>> Observable = RetrofitService.createApi(ComicApi.class).queryComicReadRDByGetObservable(comicId);
        ObservableTool.subscribe(Observable, new RetrofitCallback<List<ComicReadBean>>() {
            @Override
            public void onSuccess(List<ComicReadBean> comicReadBean) {
                mLoadingAndRetryManager.showContent();
                ArrayList<ComicReadBean.ImageListBean.ImagesBean> dataList = getDataList(comicReadBean);
                adapter.setData(dataList);
                int realPosition = getRealPosition(comicReadBean);
                recyclerView.scrollToPosition(realPosition);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                mLoadingAndRetryManager.showRetry();
            }
        });
    }

//    public void getDataFromNet() {
//        mLoadingAndRetryManager.showLoading();
//        hasCache = false;
//        RequestQueue queue = SampleVolleyFactory.getRequestQueue(this);
//        GsonRequest gsonRequest = new GsonRequest<>(AppServices.getComicReadUrl(comicId), ComicReadRD.class,
//                new Response.Listener<ComicReadRD>() {
//                    @Override
//                    public void onResponse(ComicReadRD response) {
//                        hasCache = true;
//                        mLoadingAndRetryManager.showContent();
//                        List<ComicReadRD.DataBean.ReturnDataBean> returnData = response.getData().getReturnData();
//
//                        ArrayList<ComicReadRD.DataBean.ReturnDataBean.ImageListBean.ImagesBean> dataList = getDataList(returnData);
//                        adapter.setData(dataList);
//
//                        int realPosition = getRealPosition(returnData);
//                        recyclerView.scrollToPosition(realPosition);
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
}
