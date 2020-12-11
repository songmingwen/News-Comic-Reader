package com.song.sunset.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author songmingwen
 * @description
 * @since 2020/10/21
 */
class NonSwipeableViewPager extends ViewPager {
    private boolean mScrollable;
    private OnCanScrollListener mOnCanScrollListener;

    public NonSwipeableViewPager(final Context pContext) {
        super(pContext);
    }

    public NonSwipeableViewPager(final Context pContext, final AttributeSet pAttributeSet) {
        super(pContext, pAttributeSet);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent pMotionEvent) {
        if (!mScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(pMotionEvent);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent pMotionEvent) {
        if (!mScrollable) {
            return false;
        } else {
            try {
                return super.onTouchEvent(pMotionEvent);
            }catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (mOnCanScrollListener != null) {
            return mOnCanScrollListener.canScroll(v, checkV, dx, x, y);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }

    public void setScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }

    public void setOnCanScrollListener(OnCanScrollListener mOnCanScrollListener) {
        this.mOnCanScrollListener = mOnCanScrollListener;
    }

    @FunctionalInterface
    public interface OnCanScrollListener {
        boolean canScroll(View v, boolean checkV, int dx, int x, int y);
    }
}
