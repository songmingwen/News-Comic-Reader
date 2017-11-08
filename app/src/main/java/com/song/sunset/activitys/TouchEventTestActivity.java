package com.song.sunset.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.beans.Comment;
import com.song.sunset.beans.User;
import com.song.sunset.widget.FloorView;

import java.util.ArrayList;

/**
 * Created by Song on 2016/12/2.
 * E-mail:z53520@qq.com
 */

public class TouchEventTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        FloorView floorView = (FloorView) findViewById(R.id.floor_view);
        ArrayList<Comment> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Comment comment = new Comment();
            User user = new User();
            if (i % 7 == 0) {
                user.setPhone("华为荣耀");
            } else if (i % 7 == 2) {
                user.setPhone("小米3");
            } else if (i % 7 == 4) {
                user.setPhone("iphone6s");
            } else if (i % 7 == 6) {
                user.setPhone("黑莓");
            } else {
                user.setPhone("诺基亚lumia 929");
            }
            user.setUserName("宋明文-" + i);
            user.setAddress("孝感市云梦县-" + i);
            comment.setContent("这里是评论的具体内容,你想写啥就写啥" + i);
            comment.setUser(user);
            list.add(comment);
        }
        floorView.setComments(list);
    }

    public void buttonOnClick(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("Button_touch", "-----down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("Button_touch", "-----move");
                        break;
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
            case MotionEvent.ACTION_MOVE:
                Log.i("Activity_touch", "-----move");
                break;
        }
        return super.onTouchEvent(event);
    }
}
