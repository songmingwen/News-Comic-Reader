package com.song.sunset.activitys;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.song.sunset.R;
import com.song.sunset.beans.DanmakuBean;
import com.song.sunset.beans.VideoDetailBean;
import com.song.sunset.utils.danmaku.SongDanmakuParser;
import com.song.sunset.utils.danmu.LinearGradientFontSpan;
import com.song.sunset.utils.danmu.SpannedCacheSufferAdapter;
import com.song.sunset.utils.rxjava.RxBus;
import com.song.sunset.widget.GoodsTag;
import com.song.video.DanMuVideoController;
import com.song.video.NormalVideoPlayer;
import com.song.video.Resolution;
import com.song.video.VideoManager;
import com.song.video.model.SeekEvent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class PhoenixVideoActivity extends AppCompatActivity {

    public static final String VIDEO_BEAN = "video_bean";
    private DanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mParser;
    private LinearLayout mLayout;
    private VideoDetailBean mVideoDetailBean;

    public static void start(Context context, VideoDetailBean videoDetailBean) {
        Intent intent = new Intent(context, PhoenixVideoActivity.class);
        intent.putExtra(VIDEO_BEAN, videoDetailBean);
        context.startActivity(intent);
    }

    private void getExtra() {
        if (getIntent() != null) {
            mVideoDetailBean = getIntent().getParcelableExtra(VIDEO_BEAN);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getExtra();

        setContentView(R.layout.video_detail_layout);

        initDanmakuContext();
        initDanmakuView();

        mLayout = (LinearLayout) findViewById(R.id.box);

        NormalVideoPlayer player = (NormalVideoPlayer) findViewById(R.id.id_normal_video_player);
        DanMuVideoController controller = new DanMuVideoController(this);
        controller.setTitle(mVideoDetailBean.getTitle());
        controller.setLenght(mVideoDetailBean.getDuration() * 1000);
        player.setDanMuView(mDanmakuView);
        player.setController(controller);
        Glide.with(this)
                .load(mVideoDetailBean.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(controller.imageView());
        player.setUp(mVideoDetailBean.getVideo_url(), null);
        //        controller.setResolutions(getResolutions(), 0);
        player.start();

        Disposable disposable = RxBus.getInstance().toObservable(SeekEvent.class, this).subscribe(seekEvent -> {
            if (mDanmakuView != null) {
                mDanmakuView.seekTo(seekEvent.getPosition());
            }
        });
    }

    /**
     * 多视频源使用
     */
    @NonNull
    private ArrayList<Resolution> getResolutions() {
        Resolution P = new Resolution("普清", "480", mVideoDetailBean.getVideo_url());
        Resolution Hp = new Resolution("高清清", "720", mVideoDetailBean.getVideo_url());
        Resolution Chp = new Resolution("超清清", "1080", mVideoDetailBean.getVideo_url());
        ArrayList<Resolution> list = new ArrayList<>();
        list.add(P);
        list.add(Hp);
        list.add(Chp);
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoManager.instance().resumeNormalVideoPlayer();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoManager.instance().suspendNormalVideoPlayer();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoManager.instance().releaseNormalVideoPlayer();
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
        if (VideoManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
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
        goodsTag.setData(getSpannableString("29999.9"), 0);
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
                .setCacheStuffer(new SpannedCacheStuffer(), new SpannedCacheSufferAdapter())
                //                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
                //                .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);
    }

    private void initDanmakuView() {
        mDanmakuView = new DanmakuView(this);
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
            public boolean onDanmakuLongClick(IDanmakus danmakus) {
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
        String text = "我发送的弹幕" + c.get(Calendar.HOUR_OF_DAY) + "时"
                + c.get(Calendar.MINUTE) + "分"
                + c.get(Calendar.SECOND) + "秒";

        danmaku.text = getSpannableString(text);
        danmaku.padding = 5;
        danmaku.priority = 7;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 200);
        //        danmaku.textSize = 20f * (mParser.getDisplayer().getDensity() - 0.6f);
        //        danmaku.textColor = Color.GREEN;
        danmaku.textShadowColor = Color.BLACK;
        //        danmaku.underlineColor = Color.GREEN;
//        danmaku.borderColor = Color.WHITE;
        mDanmakuView.addDanmaku(danmaku);
    }

    @NotNull
    private SpannableString getSpannableString(String text) {
        LinearGradientFontSpan linearGradientFontSpan =
                new LinearGradientFontSpan(getResources().getColor(R.color.colorPrimary),
                        getResources().getColor(R.color.colorAccent));
        SpannableString spannableString = new SpannableString(text);
        //第一个参数是格式，第二个参数起始位置，第三个结束位置（但不包括结束位置），,第四个模式，共有四种
        spannableString.setSpan(linearGradientFontSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @NonNull
    private ArrayList<DanmakuBean> getDanmakuList() {
        ArrayList<DanmakuBean> list = new ArrayList<>();
        for (int x = 0; x < 1000; x++) {
            DanmakuBean bean = new DanmakuBean();
            long time = (long) ((Math.random() * 500) * 1000 / 2);
            bean.setTime(time);
            bean.setColor(Color.WHITE);
            if (specialTime(time)) {
                bean.setType(BaseDanmaku.TYPE_SPECIAL);
            } else {
                bean.setType(BaseDanmaku.TYPE_SCROLL_RL);
            }
            bean.setTextShadowColor(Color.BLACK);
            bean.setId(String.valueOf(x));
            bean.setTextSize(20f * (this.getResources().getDisplayMetrics().density - 0.6f));
            bean.setContent("弹幕来一发666");
            bean.setLive(true);
            list.add(bean);
        }
        return list;
    }

    private boolean specialTime(long time) {
        return (time > 5000 && time < 7000) ||
                (time > 10000 && time < 12000) ||
                (time > 15000 && time < 17000);
    }
}
