package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicDetailAdapter;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitCall;
import com.song.sunset.utils.BitmapUtil;
import com.song.sunset.utils.retrofit.RetrofitUtil;
import com.song.sunset.utils.volley.SampleVolleyFactory;

import retrofit2.Call;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailActivity extends BaseActivity {

    private LoadingAndRetryManager mLoadingAndRetryManager;
    public static final String COMIC_ID = "comic_id";
    private int comicId = -1;
    private RecyclerView recyclerView;
    private ComicDetailAdapter adapter;
    private int color;
    private Toolbar toolbar;
    private boolean hasCache = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoadingAndRetryManager.showLoading();
//                        getDataFromNet();
                        getDataFromRetrofit2();
                    }
                });
            }
        });
        mLoadingAndRetryManager.showLoading();

        color = Color.WHITE;
        if (getIntent() != null) {
            comicId = getIntent().getIntExtra(COMIC_ID, -1);
        }

        recyclerView = (RecyclerView) findViewById(R.id.id_comic_detail_recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        adapter = new ComicDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int offset = recyclerView.computeVerticalScrollOffset();
                float rate = offset * 1.0f / ViewUtil.dip2px(150);
                rate = rate > 1 ? 1 : rate;
                toolbar.setAlpha(rate);
            }
        });
//        getDataFromNet();
        getDataFromRetrofit2();
    }

    public void getDataFromRetrofit2() {
        Call<BaseBean<ComicDetailBean>> call = RetrofitUtil.getRetrofit2Service().queryComicDetailRDByGetCall(comicId);
        RetrofitCall.call(call, new RetrofitCallback<ComicDetailBean>() {
            @Override
            public void onSuccess(ComicDetailBean comicDetailBean) {
                mLoadingAndRetryManager.showContent();
                toolbar.setTitle(comicDetailBean.getComic().getName());
                toolbar.setLogo(R.mipmap.logo);
                adapter.setData(comicDetailBean);
                setExtractionColorFromBitmap(comicDetailBean);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                mLoadingAndRetryManager.showRetry();
            }
        });
    }

    public static void start(Context context, int comicId) {
        Intent intent = new Intent(context, ComicDetailActivity.class);
        intent.putExtra(COMIC_ID, comicId);
        context.startActivity(intent);
    }

    public void setExtractionColorFromBitmap(ComicDetailBean comicDetailRD) {
        RequestQueue queue = SampleVolleyFactory.getRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(comicDetailRD.getComic().getCover(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        color = BitmapUtil.getColorFromBitmap(response);
                        adapter.setColor(color);
                        toolbar.setBackgroundColor(color);
                    }
                }, 128, 128, Bitmap.Config.ALPHA_8, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        imageRequest.setRetryPolicy(new DefaultRetryPolicy());
        queue.add(imageRequest);
    }

    public void getDataFromNet() {
//        hasCache = false;
//        RequestQueue queue = SampleVolleyFactory.getRequestQueue(this);
//        GsonRequest gsonRequest = new GsonRequest<>(AppServices.getComicDetailUrl(comicId), ComicDetailRD.class,
//                new Response.Listener<ComicDetailRD>() {
//                    @Override
//                    public void onResponse(ComicDetailRD response) {
//                        hasCache = true;
//                        mLoadingAndRetryManager.showContent();
//                        ComicDetailRD.DataBean.ReturnDataBean comicDetailRD = response.getData().getReturnData();
//                        toolbar.setTitle(comicDetailRD.getComic().getName());
//                        toolbar.setLogo(R.mipmap.logo);
//                        adapter.setData(comicDetailRD);
//                        setExtractionColorFromBitmap(comicDetailRD);
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
//        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1));
//        queue.add(gsonRequest);
    }
}
