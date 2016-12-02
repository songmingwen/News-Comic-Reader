package com.song.sunset.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.song.sunset.R;

/**
 * Created by songmw3 on 2016/12/2.
 */

public class TouchEventTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }

    public void buttonOnClick(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("Button_touch", "-----down");
                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i("Button_touch", "-----move");
//                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Activity_touch", "-----down");
                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("Activity_touch", "-----move");
//                break;
        }
        return super.onTouchEvent(event);
    }
}
