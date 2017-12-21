package com.song.sunset.activitys;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.song.sunset.R;
import com.song.sunset.beans.DanmakuBean;
import com.song.sunset.utils.StringUtils;
import com.song.sunset.utils.danmaku.SongDanmakuParser;
import com.song.sunset.widget.GoodsTag;
import com.song.video.SimplePlayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuTextureView;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class PhoenixVideoActivity extends AppCompatActivity {

    public static final String TV_URL = "tv_url";
    public static final String TV_NAME = "tv_name";
    public static final String TV_COVER = "tv_cover";
    private String tvUrl, tvName, tvCover;
    private DanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mParser;
    private SimplePlayer player;
    private LinearLayout mLayout;

    public static void start(Context context, String tvUrl, String tvName, String cover) {
        Intent intent = new Intent(context, PhoenixVideoActivity.class);
        intent.putExtra(TV_NAME, tvName);
        intent.putExtra(TV_URL, tvUrl);
        intent.putExtra(TV_COVER, cover);
        context.startActivity(intent);
    }

    private void getExtra() {
        if (getIntent() != null) {
            tvName = getIntent().getStringExtra(TV_NAME);
            tvUrl = getIntent().getStringExtra(TV_URL);
            tvCover = getIntent().getStringExtra(TV_COVER);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getExtra();

        setContentView(R.layout.video_detail_layout);

        mLayout = (LinearLayout) findViewById(R.id.box);
        startVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
        if (mDanmakuContext == null || mDanmakuView == null) {
            return;
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mDanmakuView.getConfig().setDanmakuMargin(20);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mDanmakuView.getConfig().setDanmakuMargin(40);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    private void startVideo() {
        if (TextUtils.isEmpty(tvUrl)) return;
        player = new SimplePlayer(this);
        player.setTitle(tvName);
        player.setCover(tvCover);
        player.play(tvUrl);
    }

    public void addDanmaku(View view) {
        if (mDanmakuContext == null || mDanmakuView == null) {
            initDanmakuContext();
            initDanmakuView();
        } else {
            if (!mDanmakuView.isShown()) {
                mDanmakuView.show();
            } else {
                addDanmaku(true);
            }
        }
        GoodsTag goodsTag = new GoodsTag(this);
        goodsTag.setData("19.9", 0);
        mLayout.addView(goodsTag);
    }

    public void closeDanmaku(View view) {
        if (mDanmakuContext != null || mDanmakuView != null) {
            if (mDanmakuView.isShown()) {
                mDanmakuView.hide();
            }
        }
        int removeIndex = mLayout.getChildCount() - 1;
        if (removeIndex >= 0) {
            mLayout.removeViewAt(removeIndex);
        }
    }

    private void initDanmakuContext() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_SHADOW, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
//                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//                .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);
    }

    private void initDanmakuView() {
        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        mParser = new SongDanmakuParser(getDanmakuList());
        mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
            @Override
            public void updateTimer(DanmakuTimer timer) {
            }

            @Override
            public void drawingFinished() {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void prepared() {
                mDanmakuView.start();
            }
        });
        mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {

            @Override
            public boolean onDanmakuClick(IDanmakus danmakus) {
                Log.d("DFM", "onDanmakuClick: danmakus size:" + danmakus.size());
                BaseDanmaku latest = danmakus.last();
                if (null != latest) {
                    Log.d("DFM", "onDanmakuClick: text of latest danmaku:" + latest.text);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onViewClick(IDanmakuView view) {
                return false;
            }
        });
        mDanmakuView.prepare(mParser, mDanmakuContext);
//        mDanmakuView.showFPS(true);
        mDanmakuView.enableDanmakuDrawingCache(true);
    }

    private void addDanmaku(boolean islive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        danmaku.text = "我发送的弹幕" + c.get(Calendar.YEAR) + "年"
                + (1 + c.get(Calendar.MONTH)) + "月"
                + c.get(Calendar.DAY_OF_MONTH) + "日"
                + c.get(Calendar.HOUR_OF_DAY) + "时"
                + c.get(Calendar.MINUTE) + "分"
                + c.get(Calendar.SECOND) + "秒";
        danmaku.padding = 5;
        danmaku.priority = 7;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        danmaku.textSize = 20f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.GREEN;
        danmaku.textShadowColor = Color.WHITE;
//        danmaku.underlineColor = Color.GREEN;
//        danmaku.borderColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

    @NonNull
    private ArrayList<DanmakuBean> getDanmakuList() {
        ArrayList<DanmakuBean> list = new ArrayList<>();
        for (int x = 0; x < 1000; x++) {
            DanmakuBean bean = new DanmakuBean();
            bean.setTime((int) (Math.random() * 500) * 1000 / 2);
            bean.setColor(Color.WHITE);
            bean.setType(BaseDanmaku.TYPE_SCROLL_RL);
            bean.setTextShadowColor(Color.BLACK);
            bean.setId(String.valueOf(x));
            bean.setTextSize(20f * (this.getResources().getDisplayMetrics().density - 0.6f));
            bean.setContent("弹幕来一发666");
            bean.setLive(true);
            list.add(bean);
        }
        return list;
    }
}
