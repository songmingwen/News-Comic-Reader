package com.song.sunset.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by songmw3 on 2016/12/2.
 */

public class TouchEventOuterViewGroup extends RelativeLayout {

    public TouchEventOuterViewGroup(Context context) {
        super(context);
    }

    public TouchEventOuterViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventOuterViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Outer_dispatch", "-----down");
                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("Outer_dispatch", "-----move");
//                break;
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Outer_intercept", "-----down");
                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("Outer_intercept", "-----move");
//                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Outer_touch", "-----down");
                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("Outer_touch", "-----move");
//                break;
        }
        return super.onTouchEvent(event);
    }
}
