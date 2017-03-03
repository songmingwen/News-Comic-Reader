package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;

/**
 * Created by z5352_000 on 2016/10/21 0021.
 */

public class VideoViewActivity extends BaseActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        Uri uri = Uri.parse("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4");
//        Uri uri = Uri.parse("rtmp://live.hkstv.hk.lxdns.com/live/hks");
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onStart() {
        videoView.stopPlayback();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, VideoViewActivity.class));
    }
}
