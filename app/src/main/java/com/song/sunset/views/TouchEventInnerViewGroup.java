package com.song.sunset.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by songmw3 on 2016/12/2.
 */

public class TouchEventInnerViewGroup extends RelativeLayout {

    public TouchEventInnerViewGroup(Context context) {
        super(context);
    }

    public TouchEventInnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventInnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Inner_dispatch", "-----down");
                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("Inner_dispatch", "-----move");
//                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Inner_Intercept", "-----down");
                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("Inner_Intercept", "-----move");
//                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Inner_touch", "-----down");
                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("Inner_touch", "-----move");
//                break;
        }
        return super.onTouchEvent(event);
    }
}
