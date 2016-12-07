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

import java.io.File;
import java.util.Date;

/**
 * Created by songmw3 on 2016/12/2.
 */

public class TempTestActivity extends BaseActivity {

    private String imgPath;
    private ImageView imgUser;
    private String selectedImagePath = "";
    final private int CAPTURE_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("temp_activity_onCreate", "----------------");
        if (savedInstanceState != null) {
            imgPath = savedInstanceState.getString("imgPath");
            super.onCreate(null);
        } else {
            super.onCreate(savedInstanceState);
        }

        setContentView(R.layout.activity_temp_test);
        imgUser = (ImageView) findViewById(R.id.backdrop);
    }

    @Override
    protected void onStart() {
        Log.i("temp_activity_onStart", "----------------");
        super.onStart();
        final Intent it = new Intent();
        it.setAction("android.intent.action.BOOST_DOWNLOADING");
        it.putExtra("package_name", " com.android.contacts ");
        it.putExtra("enabled", true);
        sendBroadcast(it);

    }

    @Override
    protected void onStop() {
        Log.i("temp_activity_onStop", "----------------");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("temp_activity_onDestroy", "----------------");
        super.onDestroy();
        final Intent it = new Intent();
        it.setAction("android.intent.action.BOOST_DOWNLOADING");
        it.putExtra("package_name", " com.android.contacts ");
        it.putExtra("enabled", false);
        sendBroadcast(it);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("temp_activity_onRestore", "----------------");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("temp_activity_onSave", "----------------");
        outState.putString("imgPath", imgPath);
        super.onSaveInstanceState(outState);
    }

    public void takePhoto(View view) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    public void takePic(View view) {

    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }


    public String getImagePath() {
        return imgPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("temp_activity_", "onActivityResult----------------");
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == CAPTURE_IMAGE) {
                selectedImagePath = getImagePath();
                imgUser.setImageBitmap(decodeFile(selectedImagePath));
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
