package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.adapters.ComicReadMVPAdapter;
import com.song.sunset.beans.ChapterListBean;
import com.song.sunset.beans.ComicReadImageListBean;
import com.song.sunset.interfaces.ComicReadView;
import com.song.sunset.mvp.presenters.ComicReadPresenter;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.widget.ComicReadMVPRecyclerView;
import com.song.sunset.widget.ScaleRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadMVPActivity extends BaseActivity implements ComicReadMVPRecyclerView.ComicLoadListener, ComicReadView, ScaleRecyclerView.OnSingleTapListener {

    public static final String OPEN_POSITION = "open_position";
    public static final String COMIC_CHAPTER_LIST = "comic_chapter_list";
    private int openPosition = -1;
    private ComicReadMVPRecyclerView recyclerView;
    private ComicReadMVPAdapter adapter;
    private ArrayList<ChapterListBean> mChapterList;
    private ComicReadPresenter mPresenter;
    private boolean fullScreen;
    private ProgressLayout progressLayout;

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

        progressLayout = (ProgressLayout)findViewById(R.id.progress);
        progressLayout.showLoading();

        if (getIntent() != null) {
            openPosition = getIntent().getIntExtra(OPEN_POSITION, -1);
            mChapterList = getIntent().getParcelableArrayListExtra(COMIC_CHAPTER_LIST);
        }

        initView();
        mPresenter = new ComicReadPresenter(this);
        mPresenter.setChapterList(mChapterList, openPosition);
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
    public void loadFirstPage(boolean success, List<ComicReadImageListBean> list) {
        if (isFinishing()) return;
        if (success) {
            adapter.setData(list);
            progressLayout.showContent();
        } else {
            progressLayout.showError(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressLayout.showLoading();
                    mPresenter.firstLoad(openPosition);
                }
            });
        }
    }

    @Override
    public void loadPreviousPage(boolean success, List<ComicReadImageListBean> list) {
        if (isFinishing()) return;
        if (success) {
            adapter.addDataAtTop(list);
        }
    }

    @Override
    public void loadNextPage(boolean success, List<ComicReadImageListBean> list) {
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
