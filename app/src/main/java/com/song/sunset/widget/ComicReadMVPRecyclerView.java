package com.song.sunset.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadMVPRecyclerView extends ScaleRecyclerView {

    public ComicReadMVPRecyclerView(Context context) {
        this(context, null);
    }

    public ComicReadMVPRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComicReadMVPRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
            if (firstVisiblePosition <= 2) {
                activeLoadTop();
            }
            int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
            int total = linearLayoutManager.getChildCount();
            if (lastVisiblePosition + 2 >= total) {
                activeLoadBottom();
            }
        }
    }

    private void activeLoadBottom() {
        if (mComicLoadListener != null) {
            mComicLoadListener.loadBottom();
        }
    }

    private void activeLoadTop() {
        if (mComicLoadListener != null) {
            mComicLoadListener.loadTop();
        }
    }

    private ComicLoadListener mComicLoadListener;

    public interface ComicLoadListener {
        void loadTop();

        void loadBottom();
    }

    public void setComicLoadListener(ComicLoadListener comicLoadListener) {
        this.mComicLoadListener = comicLoadListener;
    }
}
