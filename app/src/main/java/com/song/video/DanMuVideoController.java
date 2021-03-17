package com.song.video;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.song.sunset.R;
import com.song.sunset.utils.DateTimeUtils;
import com.song.sunset.utils.rxjava.RxBus;
import com.song.video.base.BaseVideoController;
import com.song.video.base.INormalVideoPlayer;
import com.song.video.model.SeekEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Song on 2018/2/7 0007.
 * E-mail: z53520@qq.com
 */

public class DanMuVideoController extends BaseVideoController
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, ChangeResolutionDialog.OnClarityChangedListener {

    private ImageView mImage, mCenterStart, mBack, mBattery, mRestartPause, mFullScreen;

    private LinearLayout mTop, mBatteryTime, mBottom, mLoading, mChangePosition,
            mCompleted, mError, mChangeBrightness, mChangeVolume;

    private TextView mTitle, danMuSwitch, mTime, mPosition, mDuration, mResolution,
            mLength, mLoadText, mChangePositionCurrent, mRetry, mReplay, mShare;

    private SeekBar mSeek;

    private ProgressBar mChangePositionProgress, mChangeBrightnessProgress, mChangeVolumeProgress;

    private boolean topBottomVisible;

    private int defaultResolutionsIndex;

    private CountDownTimer mDismissTopBottomCountDownTimer;

    private List<Resolution> mResolutions;

    private ChangeResolutionDialog mResolutionDialog;

    private boolean hasRegisterBatteryReceiver; // 是否已经注册了电池广播

    public DanMuVideoController(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_danmu_video_player_controller, this, true);

        mCenterStart = (ImageView) findViewById(R.id.center_start);
        mImage = (ImageView) findViewById(R.id.image);

        mTop = (LinearLayout) findViewById(R.id.top);
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mBatteryTime = (LinearLayout) findViewById(R.id.battery_time);
        danMuSwitch = (TextView) findViewById(R.id.txt_danmu_switch);
        mBattery = (ImageView) findViewById(R.id.battery);
        mTime = (TextView) findViewById(R.id.time);

        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mRestartPause = (ImageView) findViewById(R.id.restart_or_pause);
        mPosition = (TextView) findViewById(R.id.position);
        mDuration = (TextView) findViewById(R.id.duration);
        mSeek = (SeekBar) findViewById(R.id.seek);
        mFullScreen = (ImageView) findViewById(R.id.full_screen);
        mResolution = (TextView) findViewById(R.id.clarity);
        mLength = (TextView) findViewById(R.id.length);

        mLoading = (LinearLayout) findViewById(R.id.loading);
        mLoadText = (TextView) findViewById(R.id.load_text);

        mChangePosition = (LinearLayout) findViewById(R.id.change_position);
        mChangePositionCurrent = (TextView) findViewById(R.id.change_position_current);
        mChangePositionProgress = (ProgressBar) findViewById(R.id.change_position_progress);

        mChangeBrightness = (LinearLayout) findViewById(R.id.change_brightness);
        mChangeBrightnessProgress = (ProgressBar) findViewById(R.id.change_brightness_progress);

        mChangeVolume = (LinearLayout) findViewById(R.id.change_volume);
        mChangeVolumeProgress = (ProgressBar) findViewById(R.id.change_volume_progress);

        mError = (LinearLayout) findViewById(R.id.error);
        mRetry = (TextView) findViewById(R.id.retry);

        mCompleted = (LinearLayout) findViewById(R.id.completed);
        mReplay = (TextView) findViewById(R.id.replay);
        mShare = (TextView) findViewById(R.id.share);

        mCenterStart.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mRestartPause.setOnClickListener(this);
        mFullScreen.setOnClickListener(this);
        mResolution.setOnClickListener(this);
        mRetry.setOnClickListener(this);
        mReplay.setOnClickListener(this);
        mShare.setOnClickListener(this);
        danMuSwitch.setOnClickListener(this);
        mSeek.setOnSeekBarChangeListener(this);
        this.setOnClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public ImageView imageView() {
        return mImage;
    }

    @Override
    public void setImage(@DrawableRes int resId) {
        mImage.setImageResource(resId);
    }

    @Override
    public void setLenght(long length) {
        mLength.setText(DateTimeUtils.formatTime(length));
    }

    @Override
    public void setNormalVideoPlayer(INormalVideoPlayer normalVideoPlayer) {
        super.setNormalVideoPlayer(normalVideoPlayer);
        // 给播放器配置视频链接地址
        if (mResolutions != null && mResolutions.size() > 1) {
            mNormalVideoPlayer.setUp(mResolutions.get(defaultResolutionsIndex).videoUrl, null);
        }
    }

    /**
     * 设置清晰度
     *
     * @param resolutions 清晰度及链接
     */
    public void setResolutions(List<Resolution> resolutions, int defaultResolutionIndex) {
        if (resolutions != null && resolutions.size() > 1) {
            this.mResolutions = resolutions;
            this.defaultResolutionsIndex = defaultResolutionIndex;

            List<String> clarityGrades = new ArrayList<>();
            for (Resolution clarity : resolutions) {
                clarityGrades.add(clarity.grade + " " + clarity.p);
            }
            mResolution.setText(resolutions.get(defaultResolutionIndex).grade);
            // 初始化切换清晰度对话框
            mResolutionDialog = new ChangeResolutionDialog(mContext);
            mResolutionDialog.setClarityGrade(clarityGrades, defaultResolutionIndex);
            mResolutionDialog.setOnClarityCheckedListener(this);
            // 给播放器配置视频链接地址
            if (mNormalVideoPlayer != null) {
                mNormalVideoPlayer.setUp(resolutions.get(defaultResolutionIndex).videoUrl, null);
            }
        }
    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case NormalVideoPlayer.STATE_IDLE:
                break;
            case NormalVideoPlayer.STATE_PREPARING:
                mImage.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mLoadText.setText("正在准备...");
                mError.setVisibility(View.GONE);
                mCompleted.setVisibility(View.GONE);
                mTop.setVisibility(View.GONE);
                mBottom.setVisibility(View.GONE);
                mCenterStart.setVisibility(View.GONE);
                mLength.setVisibility(View.GONE);
                break;
            case NormalVideoPlayer.STATE_PREPARED:
                startUpdateProgressTimer();
                break;
            case NormalVideoPlayer.STATE_PLAYING:
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                startDismissTopBottomTimer();
                break;
            case NormalVideoPlayer.STATE_PAUSED:
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                cancelDismissTopBottomTimer();
                break;
            case NormalVideoPlayer.STATE_BUFFERING_PLAYING:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                mLoadText.setText("正在缓冲...");
                startDismissTopBottomTimer();
                break;
            case NormalVideoPlayer.STATE_BUFFERING_PAUSED:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                mLoadText.setText("正在缓冲...");
                cancelDismissTopBottomTimer();
                break;
            case NormalVideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mTop.setVisibility(View.VISIBLE);
                mError.setVisibility(View.VISIBLE);
                break;
            case NormalVideoPlayer.STATE_COMPLETED:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mImage.setVisibility(View.VISIBLE);
                mCompleted.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPlayModeChanged(int playMode) {
        switch (playMode) {
            case NormalVideoPlayer.MODE_NORMAL:
                mBack.setVisibility(View.GONE);
                mFullScreen.setImageResource(R.drawable.ic_player_enlarge);
                mFullScreen.setVisibility(View.VISIBLE);
                mResolution.setVisibility(View.GONE);
                mBatteryTime.setVisibility(View.GONE);
                if (hasRegisterBatteryReceiver) {
                    mContext.unregisterReceiver(mBatterReceiver);
                    hasRegisterBatteryReceiver = false;
                }
                break;
            case NormalVideoPlayer.MODE_FULL_SCREEN:
                mBack.setVisibility(View.VISIBLE);
//                mFullScreen.setVisibility(View.GONE);
                mFullScreen.setImageResource(R.drawable.ic_player_shrink);
                if (mResolutions != null && mResolutions.size() > 1) {
                    mResolution.setVisibility(View.VISIBLE);
                }
                mBatteryTime.setVisibility(View.VISIBLE);
                if (!hasRegisterBatteryReceiver) {
                    mContext.registerReceiver(mBatterReceiver,
                            new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    hasRegisterBatteryReceiver = true;
                }
                break;
            case NormalVideoPlayer.MODE_TINY_WINDOW:
                mBack.setVisibility(View.VISIBLE);
                mResolution.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 电池状态即电量变化广播接收器
     */
    private BroadcastReceiver mBatterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                    BatteryManager.BATTERY_STATUS_UNKNOWN);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                // 充电中
                mBattery.setImageResource(R.drawable.battery_charging);
            } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                // 充电完成
                mBattery.setImageResource(R.drawable.battery_full);
            } else {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int percentage = (int) (((float) level / scale) * 100);
                if (percentage <= 10) {
                    mBattery.setImageResource(R.drawable.battery_10);
                } else if (percentage <= 20) {
                    mBattery.setImageResource(R.drawable.battery_20);
                } else if (percentage <= 50) {
                    mBattery.setImageResource(R.drawable.battery_50);
                } else if (percentage <= 80) {
                    mBattery.setImageResource(R.drawable.battery_80);
                } else if (percentage <= 100) {
                    mBattery.setImageResource(R.drawable.battery_100);
                }
            }
        }
    };

    @Override
    public void reset() {
        topBottomVisible = false;
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        mSeek.setProgress(0);
        mSeek.setSecondaryProgress(0);

        mCenterStart.setVisibility(View.VISIBLE);
        mImage.setVisibility(View.VISIBLE);

        mBottom.setVisibility(View.GONE);
        mFullScreen.setImageResource(R.drawable.ic_player_enlarge);

        mLength.setVisibility(View.VISIBLE);

        mTop.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.GONE);

        mLoading.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mCompleted.setVisibility(View.GONE);
    }

    /**
     * 尽量不要在onClick中直接处理控件的隐藏、显示及各种UI逻辑。
     * UI相关的逻辑都尽量到{@link #onPlayStateChanged}和{@link #onPlayModeChanged}中处理.
     */
    @Override
    public void onClick(View v) {
        if (v == danMuSwitch) {
            String close = mContext.getResources().getString(R.string.string_close_danmu);
            String open = mContext.getResources().getString(R.string.string_open_danmu);
            String showText = (String) danMuSwitch.getText();
            if (TextUtils.equals(open, showText)) {
                danMuSwitch.setText(close);
                mNormalVideoPlayer.openDanMu();
            } else {
                danMuSwitch.setText(open);
                mNormalVideoPlayer.closeDanMu();
            }

        } else if (v == mCenterStart) {
            if (mNormalVideoPlayer.isIdle()) {
                mNormalVideoPlayer.start();
            }
        } else if (v == mBack) {
            if (mNormalVideoPlayer.isFullScreen()) {
                mNormalVideoPlayer.exitFullScreen();
            } else if (mNormalVideoPlayer.isTinyWindow()) {
                mNormalVideoPlayer.exitTinyWindow();
            }
        } else if (v == mRestartPause) {
            if (mNormalVideoPlayer.isPlaying() || mNormalVideoPlayer.isBufferingPlaying()) {
                mNormalVideoPlayer.pause();
            } else if (mNormalVideoPlayer.isPaused() || mNormalVideoPlayer.isBufferingPaused()) {
                mNormalVideoPlayer.restart();
            }
        } else if (v == mFullScreen) {
            if (mNormalVideoPlayer.isNormal() || mNormalVideoPlayer.isTinyWindow()) {
                mNormalVideoPlayer.enterFullScreen();
            } else if (mNormalVideoPlayer.isFullScreen()) {
                mNormalVideoPlayer.exitFullScreen();
            }
        } else if (v == mResolution) {
            setTopBottomVisible(false); // 隐藏top、bottom
            mResolutionDialog.show();     // 显示清晰度对话框
        } else if (v == mRetry) {
            mNormalVideoPlayer.restart();
        } else if (v == mReplay) {
            mRetry.performClick();
        } else if (v == mShare) {
            Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
        } else if (v == this) {
            if (mNormalVideoPlayer.isPlaying()
                    || mNormalVideoPlayer.isPaused()
                    || mNormalVideoPlayer.isBufferingPlaying()
                    || mNormalVideoPlayer.isBufferingPaused()) {
                setTopBottomVisible(!topBottomVisible);
            }
        }
    }

    @Override
    public void onClarityChanged(int clarityIndex) {
        // 根据切换后的清晰度索引值，设置对应的视频链接地址，并从当前播放位置接着播放
        Resolution clarity = mResolutions.get(clarityIndex);
        mResolution.setText(clarity.grade);
        long currentPosition = mNormalVideoPlayer.getCurrentPosition();
        mNormalVideoPlayer.releasePlayer();
        mNormalVideoPlayer.setUp(clarity.videoUrl, null);
        mNormalVideoPlayer.start(currentPosition);
    }

    @Override
    public void onClarityNotChanged() {
        // 清晰度没有变化，对话框消失后，需要重新显示出top、bottom
        setTopBottomVisible(true);
    }

    /**
     * 设置top、bottom的显示和隐藏
     *
     * @param visible true显示，false隐藏.
     */
    private void setTopBottomVisible(boolean visible) {
        mTop.setVisibility(visible ? View.VISIBLE : View.GONE);
        mBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
        topBottomVisible = visible;
        if (visible) {
            if (!mNormalVideoPlayer.isPaused() && !mNormalVideoPlayer.isBufferingPaused()) {
                startDismissTopBottomTimer();
            }
        } else {
            cancelDismissTopBottomTimer();
        }
    }

    /**
     * 开启top、bottom自动消失的timer
     */
    private void startDismissTopBottomTimer() {
        cancelDismissTopBottomTimer();
        if (mDismissTopBottomCountDownTimer == null) {
            mDismissTopBottomCountDownTimer = new CountDownTimer(8000, 8000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setTopBottomVisible(false);
                }
            };
        }
        mDismissTopBottomCountDownTimer.start();
    }

    /**
     * 取消top、bottom自动消失的timer
     */
    private void cancelDismissTopBottomTimer() {
        if (mDismissTopBottomCountDownTimer != null) {
            mDismissTopBottomCountDownTimer.cancel();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mNormalVideoPlayer.isBufferingPaused() || mNormalVideoPlayer.isPaused()) {
            mNormalVideoPlayer.restart();
        }
        long position = (long) (mNormalVideoPlayer.getDuration() * seekBar.getProgress() / 100f);
        mNormalVideoPlayer.seekTo(position);
        RxBus.getInstance().post(new SeekEvent(position));
        startDismissTopBottomTimer();
    }

    @Override
    protected void updateProgress() {
        long position = mNormalVideoPlayer.getCurrentPosition();
        long duration = mNormalVideoPlayer.getDuration();
        int bufferPercentage = mNormalVideoPlayer.getBufferPercentage();
        mSeek.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeek.setProgress(progress);
        mPosition.setText(DateTimeUtils.formatTime(position));
        mDuration.setText(DateTimeUtils.formatTime(duration));
        // 更新时间
        mTime.setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date()));
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {
        mChangePosition.setVisibility(View.VISIBLE);
        long newPosition = (long) (duration * newPositionProgress / 100f);
        mChangePositionCurrent.setText(DateTimeUtils.formatTime(newPosition));
        mChangePositionProgress.setProgress(newPositionProgress);
        mSeek.setProgress(newPositionProgress);
        mPosition.setText(DateTimeUtils.formatTime(newPosition));
    }

    @Override
    protected void hideChangePosition() {
        mChangePosition.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {
        mChangeVolume.setVisibility(View.VISIBLE);
        mChangeVolumeProgress.setProgress(newVolumeProgress);
    }

    @Override
    protected void hideChangeVolume() {
        mChangeVolume.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {
        mChangeBrightness.setVisibility(View.VISIBLE);
        mChangeBrightnessProgress.setProgress(newBrightnessProgress);
    }

    @Override
    protected void hideChangeBrightness() {
        mChangeBrightness.setVisibility(View.GONE);
    }
}
