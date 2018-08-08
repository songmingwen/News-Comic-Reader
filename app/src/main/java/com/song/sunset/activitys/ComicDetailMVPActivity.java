package com.song.sunset.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.song.core.base.CoreBaseActivity;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicDetailAdapter;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.mvp.models.ComicDetailModel;
import com.song.sunset.mvp.presenters.ComicDetailPresenter;
import com.song.sunset.utils.BitmapUtil;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.volley.SampleVolleyFactory;
import com.song.sunset.mvp.views.ComicDetailView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.song.sunset.adapters.ComicDetailAdapter.COMIC_LIST_TYPE;

/**
 * Created by Song on 2016/12/8.
 * E-mail:z53520@qq.com
 */

public class ComicDetailMVPActivity extends CoreBaseActivity<ComicDetailPresenter, ComicDetailModel> implements ComicDetailView {

    @BindView(R.id.id_comic_detail_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_comic_detail_fab)
    FloatingActionButton floatingActionButton;

    private ProgressLayout progressLayout;
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

    public static void start(Context context, int comicId, View view) {
        Intent intent = new Intent(context, ComicDetailMVPActivity.class);
        intent.putExtra(COMIC_ID, comicId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle comic_cover = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "comic_cover").toBundle();
            ActivityCompat.startActivity(context, intent, comic_cover);
        } else {
            start(context, comicId);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comic_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        progressLayout = (ProgressLayout) findViewById(R.id.progress);
        progressLayout.showLoading();

        color = Color.WHITE;
        if (getIntent() != null) {
            comicId = getIntent().getIntExtra(COMIC_ID, -1);
        }

        adapter = new ComicDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == COMIC_LIST_TYPE) {
                    return 1;
                } else {
                    return 3;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        final int[] distance = {0};
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                distance[0] += dy;
                int offset = distance[0];
                float rate = offset * 1.0f / ViewUtil.dip2px(200);
                rate = rate > 1 ? 1 : rate;
                rate = rate < 0 ? 0 : rate;
                toolbar.setAlpha(rate);
            }
        });

        mPresenter.showData(comicId);
        mPresenter.showCollectedStated(comicId);
    }

    @OnClick(R.id.id_comic_detail_fab)
    public void onClick() {
        mPresenter.changeCollectedState(comicDetailBean);
    }

    @Override
    public void setData(ComicDetailBean comicDetailBean) {
        this.comicDetailBean = comicDetailBean;
        progressLayout.showContent();
        toolbar.setTitle(comicDetailBean.getComic().getName());
//        toolbar.setLogo(R.mipmap.logo);
        adapter.setData(comicDetailBean);
        setExtractionColorFromBitmap(comicDetailBean);
        mPresenter.updateCollectedComicData(comicDetailBean);
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
        progressLayout.showError(v -> {
            progressLayout.showLoading();
            mPresenter.showData(comicId);
        });
    }

    public void setExtractionColorFromBitmap(ComicDetailBean comicDetailRD) {
        FrescoUtil.getCachedImageBitmap(FrescoUtil.getDataSource(comicDetailRD.getComic().getCover()), new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (ComicDetailMVPActivity.this.isFinishing()) return;
                color = BitmapUtil.getColorFromBitmap(bitmap);
                adapter.setColor(color);
                toolbar.setBackgroundColor(color);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
