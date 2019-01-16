package com.song.sunset.activitys.temp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.fragments.ComicClassifyFragment;

public class ScrollingActivity extends BaseActivity {

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ScrollingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchFragment(ComicClassifyFragment.class.getName(), R.id.activity_framelayout_scroll);
            }
        }, 0);
    }

}
