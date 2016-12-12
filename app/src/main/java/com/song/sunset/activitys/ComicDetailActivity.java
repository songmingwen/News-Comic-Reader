package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicDetailAdapter;
import com.song.sunset.utils.retrofit.ObservableTool;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.BitmapUtil;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.ComicApi;
import com.song.sunset.utils.volley.SampleVolleyFactory;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import rx.Observable;

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
    private FloatingActionButton floatingActionButton;
    private ComicLocalCollectionDao comicLocalCollectionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        comicLocalCollectionDao = SunsetApplication.getSunsetApplication().getDaoSession().getComicLocalCollectionDao();

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoadingAndRetryManager.showLoading();
                        getDataFromRetrofit2ByObservable();
                    }
                });
            }
        });
        mLoadingAndRetryManager.showLoading();

        color = Color.WHITE;
        if (getIntent() != null) {
            comicId = getIntent().getIntExtra(COMIC_ID, -1);
        }

        floatingActionButton = (FloatingActionButton) findViewById(R.id.id_comic_detail_fab);

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
        getDataFromRetrofit2ByObservable();
    }

//    public void getDataFromRetrofit2() {
//        Call<BaseBean<ComicDetailBean>> call = RetrofitApiBuilder.getRetrofitApi(RetrofitApi.class).queryComicDetailRDByGetCall(comicId);
//        RetrofitCall.call(call, new RetrofitCallback<ComicDetailBean>() {
//            @Override
//            public void onSuccess(ComicDetailBean comicDetailBean) {
//                mLoadingAndRetryManager.showContent();
//                setFloatButtonOnClick(comicDetailBean.getComic());
//                toolbar.setTitle(comicDetailBean.getComic().getName());
//                toolbar.setLogo(R.mipmap.logo);
//                adapter.setData(comicDetailBean);
//                setExtractionColorFromBitmap(comicDetailBean);
//            }
//
//            @Override
//            public void onFailure(int errorCode, String errorMsg) {
//                mLoadingAndRetryManager.showRetry();
//            }
//        });
//    }

    public void getDataFromRetrofit2ByObservable() {
        Observable<BaseBean<ComicDetailBean>> observable = RetrofitService.createApi(ComicApi.class).queryComicDetailRDByGetObservable(comicId);
        ObservableTool.subscribe(observable, new RetrofitCallback<ComicDetailBean>() {
            @Override
            public void onSuccess(ComicDetailBean comicDetailBean) {
                mLoadingAndRetryManager.showContent();
                setFloatButtonOnClick(comicDetailBean.getComic());
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

    private void setFloatButtonOnClick(final ComicDetailBean.ComicBean comic) {
        final long comicId = Long.parseLong(comic.getComic_id());
        if (comicLocalCollectionDao.load(comicId) == null) {
            floatingActionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.star_big_off));
        } else {
            floatingActionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.star_big_on));
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comicLocalCollectionDao == null) {
                    return;
                }
                if (comicLocalCollectionDao.load(comicId) == null) {
                    comicLocalCollectionDao.insert(new ComicLocalCollection(comic.getCover(), comic.getName(), comicId, comic.getDescription(), comic.getAuthor().getName()));
                    floatingActionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.star_big_on));
                    Toast.makeText(getApplication(), "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    comicLocalCollectionDao.deleteByKey(comicId);
                    floatingActionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.star_big_off));
                    Toast.makeText(getApplication(), "取消收藏", Toast.LENGTH_SHORT).show();
                }
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
}
