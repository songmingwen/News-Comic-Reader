package com.song.sunset.activitys;

/**
 * Created by songmw3 on 2017/1/3.
 */

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.song.sunset.R;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class VideoActivity extends AppCompatActivity {
    public static final String TV_URL = "tv_url";
    public static final String TV_NAME = "tv_name";
    public static final String TV_COVER = "tv_cover";
    private String tvUrl;
    private String tvName;
    private String tvCover;
    private PlayerView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = LayoutInflater.from(this).inflate(R.layout.simple_player_view_player, null);
        setContentView(rootView);
        if (getIntent() != null) {
            tvName = getIntent().getStringExtra(TV_NAME);
            tvUrl = getIntent().getStringExtra(TV_URL);
            tvCover = getIntent().getStringExtra(TV_COVER);
        }
        player = new PlayerView(this, rootView)
                .setTitle(tvName)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .forbidTouch(false)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(VideoActivity.this)
                                .load(tvCover)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(tvUrl)
                .startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
//        //屏幕旋转隐藏或显示状态栏
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
//            ScreenUtils.fullscreen(this, true);
//        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
//            ScreenUtils.fullscreen(this, false);
//        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    public static void start(Context context, String tvUrl, String tvName, String cover) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(TV_NAME, tvName);
        intent.putExtra(TV_URL, tvUrl);
        intent.putExtra(TV_COVER, cover);
        context.startActivity(intent);
    }
}
