package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.song.core.base.CoreBaseActivity;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicDetailAdapter;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.model.ComicDetailModel;
import com.song.sunset.presenter.ComicDetailPresenter;
import com.song.sunset.utils.BitmapUtil;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.utils.volley.SampleVolleyFactory;
import com.song.sunset.view.ComicDetailView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songmw3 on 2016/12/8.
 * E-mail:z53520@qq.com
 */

public class ComicDetailMVPActivity extends CoreBaseActivity<ComicDetailPresenter, ComicDetailModel> implements ComicDetailView {

    @Bind(R.id.id_comic_detail_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_comic_detail_fab)
    FloatingActionButton floatingActionButton;

    private LoadingAndRetryManager mLoadingAndRetryManager;
    public static final String COMIC_ID = "comic_id";
    private ComicDetailAdapter adapter;
    private int comicId = -1;
    private int color;

    private ComicDetailBean comicDetailBean;

    public static void start(Context context, int comicId) {
        Intent intent = new Intent(context, ComicDetailMVPActivity.class);
        intent.putExtra(COMIC_ID, comicId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoadingAndRetryManager.showLoading();
                        mPresenter.showData(comicId);
                    }
                });
            }
        });
        mLoadingAndRetryManager.showLoading();

        color = Color.WHITE;
        if (getIntent() != null) {
            comicId = getIntent().getIntExtra(COMIC_ID, -1);
        }

        adapter = new ComicDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int offset = recyclerView.computeVerticalScrollOffset();
                float rate = offset * 1.0f / ViewUtil.dip2px(150);
                rate = rate > 1 ? 1 : rate;
                toolbar.setAlpha(rate);
            }
        });

        mPresenter.showData(comicId);
        mPresenter.showCollecteState(comicId);
    }

    @OnClick(R.id.id_comic_detail_fab)
    public void onClick() {
        mPresenter.changeCollectedState(comicId, comicDetailBean);
    }

    @Override
    public void setData(ComicDetailBean comicDetailBean) {
        this.comicDetailBean = comicDetailBean;
        mLoadingAndRetryManager.showContent();
        toolbar.setTitle(comicDetailBean.getComic().getName());
        toolbar.setLogo(R.mipmap.logo);
        adapter.setData(comicDetailBean);
        setExtractionColorFromBitmap(comicDetailBean);
    }

    @Override
    public void showCollected(boolean shouldShow, boolean shouldShowMsg) {
        if (shouldShowMsg) {
            showToast(shouldShow ? "收藏成功" : "取消收藏");
        }
        int res = shouldShow ? android.R.drawable.star_big_on : android.R.drawable.star_big_off;
        floatingActionButton.setImageDrawable(getResources().getDrawable(res));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String msg) {
        mLoadingAndRetryManager.showRetry();
    }

    public void setExtractionColorFromBitmap(ComicDetailBean comicDetailRD) {
        RequestQueue queue = SampleVolleyFactory.getRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(comicDetailRD.getComic().getCover(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        if (ComicDetailMVPActivity.this.isFinishing()) return;
                        color = BitmapUtil.getColorFromBitmap(response);
                        adapter.setColor(color);
                        toolbar.setBackgroundColor(color);
                    }
                }, 64, 64, Bitmap.Config.ALPHA_8, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        imageRequest.setRetryPolicy(new DefaultRetryPolicy());
        queue.add(imageRequest);
    }
}
