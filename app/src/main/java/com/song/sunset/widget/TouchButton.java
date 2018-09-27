package com.song.sunset.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author songmingwen
 * @since 2018/8/10
 */
public class TouchButton extends LinearLayout {
    public TouchButton(Context context) {
        super(context);
    }

    public TouchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Button_dispatch", "-----down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Button_dispatch", "-----move");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Button_touch", "-----down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Button_touch", "-----move");
                break;
        }
        return super.onTouchEvent(event);
    }
}
