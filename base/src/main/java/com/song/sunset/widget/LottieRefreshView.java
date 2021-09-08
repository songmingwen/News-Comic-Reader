package com.song.sunset.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.song.sunset.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

/**
 * @author songmingwen
 * @description
 * @since 2021/1/5
 */
public class LottieRefreshView extends FrameLayout {
    private static final String TAG = "LottieRefreshView";

    private LottieAnimationView mAnimationView;

    private boolean mRefreshing = false;

    Animation.AnimationListener mListener;


    public LottieRefreshView(@NonNull Context context) {
        this(context, null);
    }

    public LottieRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LottieRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.lottie_refresh_view, this);
        mAnimationView = findViewById(R.id.animation_view);

        setLottieAnimation(R.raw.json_interest_kanshan);
    }

    public void setLottieAnimation(String assetName) {
        mAnimationView.setAnimation(assetName);
    }

    public void setLottieAnimation(@RawRes final int rawRes) {
        mAnimationView.setAnimation(rawRes);
    }

    private int dp2px(float dp) {
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) (metrics.density * dp + 0.5f);
    }


    public void setAnimationListener(Animation.AnimationListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        if (mListener != null) {
            mListener.onAnimationStart(getAnimation());
        }
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (mListener != null) {
            mListener.onAnimationEnd(getAnimation());
        }
    }


    public void setProgress(float adjustedPercent, float tensionPercent) {
        Log.i(TAG, "setProgress: ");
        if (!mAnimationView.isAnimating()) {
//            mAnimationView.playAnimation();
            float progress = adjustedPercent / 2 + tensionPercent;
            mAnimationView.setProgress(progress);
        }
        if (!mRefreshing && adjustedPercent >= 1) {
            mRefreshing = true;

            //震动反馈
//            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
//                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        }
    }


    public void onCancel() {
        Log.i(TAG, "onCancel: ");
        mRefreshing = false;
        mAnimationView.pauseAnimation();
    }

    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        mAnimationView.playAnimation();
    }

    public void reset() {
        Log.i(TAG, "reset: ");
        mRefreshing = false;
        mAnimationView.pauseAnimation();
    }

    public int getProgressViewHeight() {
        return dp2px(56);
    }
}
