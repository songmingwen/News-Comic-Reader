package com.song.sunset.activitys;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.song.sunset.R;
import com.song.sunset.views.CustomScrollView;

import java.io.File;
import java.util.Date;

/**
 * Created by songmw3 on 2016/12/2.
 */

public class TempTestActivity extends BaseActivity {

    private CustomScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        scrollView = (CustomScrollView) findViewById(R.id.scrollView);
    }

    public void onClick(View view) {
        scrollView.addViews();
    }
}
