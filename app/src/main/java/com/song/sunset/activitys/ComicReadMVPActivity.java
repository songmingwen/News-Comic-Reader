package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.adapters.ComicReadMVPAdapter;
import com.song.sunset.beans.ChapterListBean;
import com.song.sunset.beans.ComicReadChapterBean;
import com.song.sunset.beans.ComicReadImageListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.impls.ComicReadView;
import com.song.sunset.presenter.ComicReadPresenter;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.widget.ComicReadMVPRecyclerView;
import com.song.sunset.widget.ScaleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadMVPActivity extends BaseActivity implements ComicReadMVPRecyclerView.ComicLoadListener, ComicReadView, ScaleRecyclerView.OnSingleTapListener {

    public static final String OPEN_POSITION = "open_position";
    public static final String COMIC_CHAPTER_LIST = "comic_chapter_list";
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private int openPosition = -1;
    private ComicReadMVPRecyclerView recyclerView;
    private ComicReadMVPAdapter adapter;
    private ArrayList<ChapterListBean> mChapterList;
    private ComicReadPresenter mPresenter;
    private boolean fullScreen;

    public static void start(Context context, int openPosition, ArrayList<ChapterListBean> data) {
        Intent intent = new Intent(context, ComicReadMVPActivity.class);
        intent.putExtra(OPEN_POSITION, openPosition);
        intent.putParcelableArrayListExtra(COMIC_CHAPTER_LIST, data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_read_mvp);
        switchScreenStatus();

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPresenter != null) {
                            mLoadingAndRetryManager.showLoading();
                            mPresenter.firstLoad(openPosition);
                        }
                    }
                });
            }
        });

        if (getIntent() != null) {
            openPosition = getIntent().getIntExtra(OPEN_POSITION, -1);
            mChapterList = getIntent().getParcelableArrayListExtra(COMIC_CHAPTER_LIST);
        }

        initView();
        mPresenter = new ComicReadPresenter(this);
        mPresenter.setChapterList(mChapterList, openPosition);
        mLoadingAndRetryManager.showLoading();
    }

    private void switchScreenStatus() {
        fullScreen = !fullScreen;
        ScreenUtils.fullscreen(this, fullScreen);
    }

    private void initView() {
        recyclerView = (ComicReadMVPRecyclerView) findViewById(R.id.id_comic_read_recycler);
        adapter = new ComicReadMVPAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(getLinearLayoutManager());
        recyclerView.setComicLoadListener(this);
        recyclerView.setOnSingleTapListener(this);
    }

    @NonNull
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth() / 4;
            }
        };
    }

    /**
     * RecyclerView触发顶部数据刷新
     */
    @Override
    public void loadTop() {
        if (mPresenter != null) {
            mPresenter.loadTop();
        }
    }

    /**
     * RecyclerView触发底部数据刷新
     */
    @Override
    public void loadBottom() {
        if (mPresenter != null) {
            mPresenter.loadBottom();
        }
    }

    @Override
    public void loadFirstEnd(boolean success, List<ComicReadImageListBean> list) {
        if (isFinishing()) return;
        if (success) {
            adapter.setData(list);
            mLoadingAndRetryManager.showContent();
        } else {
            mLoadingAndRetryManager.showRetry();
        }
    }

    @Override
    public void loadTopEnd(boolean success, List<ComicReadImageListBean> list) {
        if (isFinishing()) return;
        if (success) {
            adapter.addDataAtTop(list);
        }
    }

    @Override
    public void loadBottomEnd(boolean success, List<ComicReadImageListBean> list) {
        if (isFinishing()) return;
        if (success) {
            adapter.addDataAtBottom(list);
        }
    }

    @Override
    public void onSingleTap() {
        switchScreenStatus();
    }
}
