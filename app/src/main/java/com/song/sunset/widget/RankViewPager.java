package com.song.sunset.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Song on 2017/7/20 0020.
 * E-mail: z53520@qq.com
 */

public class RankViewPager extends ViewPager {
    public RankViewPager(Context context) {
        super(context);
    }

    public RankViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float startX = 0;
    float startY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
//当滑动到ViewPager的第0个页面，并且是从左到右滑动
                    if (getCurrentItem() == 0 && distanceX > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
//当滑动到ViewPager的最后一个页面，并且是从右到左滑动
                    else if ((getCurrentItem() == (getAdapter().getCount() - 1)) && distanceX < 0) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
//其他,中间部分
                    else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
//竖直方向滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
