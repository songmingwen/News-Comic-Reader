package com.song.sunset.widget;

import static android.view.animation.Animation.INFINITE;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.song.sunset.R;
import com.song.sunset.base.AppConfig;
import com.song.sunset.utils.ScreenUtils;

/**
 * Desc:    圆角矩形水波扩散自定义 view
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2024/5/11
 */

public class GleeButtonRippleView extends FrameLayout {

    //362 为内容实际宽度，加上边框厚度 8
    private static final int WIDTH_MIN = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 362 + 8);
    //动画最大宽度
    private static final int WIDTH_MAX = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 390);
    //40 为内容实际高度，加上边框厚度 8
    private static final int HIGH_MIN = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 40 + 8);
    //动画最大高度
    private static final int HIGH_MAX = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 68);
    private static final int RADIUS_MIN = HIGH_MIN / 2;
    private static final int RADIUS_MAX = HIGH_MAX / 2;
    private static final int STROKE_MIN = 0;
    private static final int STROKE_MAX = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 4);
    private View one, two;
    private ValueAnimator animatorOne, animatorTwo;

    public GleeButtonRippleView(@NonNull Context context) {
        this(context, null);
    }

    public GleeButtonRippleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GleeButtonRippleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_glee_button_ripple, this);
        one = findViewById(R.id.one_water);
        two = findViewById(R.id.two_water);
    }

    public void startAnim() {
        if (one == null || two == null) {
            return;
        }
        animatorOne = ValueAnimator.ofInt(0, 50, 100, 100);
        animatorOne.setRepeatCount(INFINITE);
        animatorOne.setDuration(1000);
        animatorOne.setInterpolator(new DecelerateInterpolator());
        animatorOne.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            float progress = value / 100f;
            renderViewByProgress(one, progress);
        });

        animatorTwo = ValueAnimator.ofInt(0, 50, 100, 100);
        animatorTwo.setRepeatCount(INFINITE);
        animatorTwo.setDuration(1000);
        animatorTwo.setInterpolator(new DecelerateInterpolator());
        animatorTwo.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            float progress = value / 100f;
            renderViewByProgress(two, progress);
        });
        animatorTwo.setStartDelay(150);

        animatorOne.start();
        animatorTwo.start();
    }

    public void stopAnim() {
        if (animatorOne != null) {
            animatorOne.cancel();
            animatorOne = null;
        }
        if (animatorTwo != null) {
            animatorTwo.cancel();
            animatorTwo = null;
        }
    }

    private void renderViewByProgress(View view, float progress) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = getWidth(progress);
        layoutParams.height = getHeight(progress);
        view.setLayoutParams(layoutParams);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setCornerRadius(getRadius(progress));
        gradientDrawable.setStroke(getStrokeWidth(progress), getResources().getColor(R.color.color_6b0aff_80));

        view.setBackground(gradientDrawable);

        view.setAlpha(1 - progress);
    }

    private int getStrokeWidth(float progress) {
        int dS = STROKE_MAX - STROKE_MIN;
        return (int) (STROKE_MAX - dS * progress);
    }

    private float getRadius(float progress) {
        int dD = RADIUS_MAX - RADIUS_MIN;
        return RADIUS_MIN + (dD * progress);
    }

    private int getHeight(float progress) {
        int dHigh = HIGH_MAX - HIGH_MIN;
        return (int) (HIGH_MIN + (dHigh * progress));
    }

    private int getWidth(float progress) {
        int dWidth = WIDTH_MAX - WIDTH_MIN;
        return (int) (WIDTH_MIN + (dWidth * progress));
    }
}
