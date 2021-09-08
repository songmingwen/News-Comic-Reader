package com.song.sunset.comic.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Song on 2017/7/20 0020.
 * E-mail: z53520@qq.com
 */

public class RankViewPager extends ViewPager {

    private float mLastMotionX;
    private float mLastMotionY;

    public RankViewPager(Context context) {
        super(context);
    }

    public RankViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionX = ev.getX();
                mLastMotionY = ev.getY();
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = ev.getX();
                mLastMotionY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (getParent() != null) {
                    float xPosition = ev.getX();
                    float yPosition = ev.getY();
                    float detX = Math.abs(xPosition - mLastMotionX);
                    float detY = Math.abs(yPosition - mLastMotionY);
                    if (getCurrentItem() <= 0 && (xPosition - mLastMotionX) > 0 && detX > detY) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
