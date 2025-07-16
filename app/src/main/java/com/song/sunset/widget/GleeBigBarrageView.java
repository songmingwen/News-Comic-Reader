package com.song.sunset.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.opensource.svgaplayer.SVGAImageView;
import com.song.sunset.R;
import com.song.sunset.base.AppConfig;
import com.song.sunset.utils.ScreenUtils;

/**
 * Desc:    芒果大弹幕
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2024/5/10
 */

public class GleeBigBarrageView extends FrameLayout {

    private static final int WIDTH_CONTAINER_FIRST = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 149);
    private static final int WIDTH_CONTAINER_SECOND = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 362);
    private static final int HIGH_CONTAINER_FIRST = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 40);
    private static final int HIGH_CONTAINER_SECOND = (int) ScreenUtils.dp2Px(AppConfig.getApp(), 159);

    private MotionLayout motionLayout;
    private LinearLayout container, topContainer, bottomContainer;
    private ImageView logo;
    private SVGAImageView mike;
    private GleeButtonRippleView rippleView;

    public GleeBigBarrageView(@NonNull Context context) {
        this(context, null);
    }

    public GleeBigBarrageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GleeBigBarrageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_glee_big_barrage, this);
        rippleView = findViewById(R.id.ripple);
        motionLayout = findViewById(R.id.glee_motion_layout);
        container = findViewById(R.id.glee_container);
        topContainer = findViewById(R.id.top_container);
        bottomContainer = findViewById(R.id.bottom_container);
        logo = findViewById(R.id.logo);
        mike = findViewById(R.id.mike);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setSelected(true);
    }

    public void initData() {

    }

    /**
     * 执行第一段动画,合唱团 logo 展示
     */
    public void startFirstAnim() {
        motionLayout.setVisibility(VISIBLE);
        mike.startAnimation();
        //麦克风展示
        ObjectAnimator mikeShow = ObjectAnimator.ofFloat(mike, "alpha", 0, 1);
        mikeShow.setDuration(300);

        //背景渐现
        ObjectAnimator containerShowAnim = ObjectAnimator.ofFloat(container, "alpha", 0, 0, 1);
        containerShowAnim.setDuration(600);

        //背景展开
        ValueAnimator containerExpendAnim = ValueAnimator.ofInt(0, 0, WIDTH_CONTAINER_FIRST);
        containerExpendAnim.setDuration(600);
        ViewGroup.LayoutParams containerParams = container.getLayoutParams();
        containerExpendAnim.addUpdateListener(animation -> {
            containerParams.width = (int) animation.getAnimatedValue();
            container.setLayoutParams(containerParams);
        });

        //logo展示
        ObjectAnimator logoShow = ObjectAnimator.ofFloat(logo, "alpha", 0, 0, 0, 1);
        logoShow.setDuration(600);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mikeShow, containerShowAnim, containerExpendAnim, logoShow);
        animatorSet.start();
    }

    /**
     * 执行第二段动画,内容向左展开，logo 移动到顶部
     */
    public void startSecondAnim() {
        //logo 移动
        motionLayout.transitionToEnd();
        //背景展开
        ValueAnimator containerExpendAnim = ValueAnimator.ofInt(
                WIDTH_CONTAINER_FIRST, WIDTH_CONTAINER_SECOND);
        containerExpendAnim.setDuration(400);
        ViewGroup.LayoutParams containerParams = container.getLayoutParams();
        containerExpendAnim.addUpdateListener(animation -> {
            containerParams.width = (int) animation.getAnimatedValue();
            container.setLayoutParams(containerParams);
        });
        containerExpendAnim.start();

        //title 展示
        ObjectAnimator titleShowAnim = ObjectAnimator.ofFloat(topContainer, "alpha", 0, 0, 0, 1);
        titleShowAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                topContainer.setVisibility(VISIBLE);
            }
        });
        titleShowAnim.setDuration(400);
        titleShowAnim.start();
    }

    /**
     * 执行第三段动画，内容向下展开
     */
    public void startThirdAnim() {
        //背景展开
        ValueAnimator containerExpendAnim = ValueAnimator.ofInt(
                HIGH_CONTAINER_FIRST, HIGH_CONTAINER_SECOND);
        containerExpendAnim.setDuration(600);
        ViewGroup.LayoutParams containerParams = container.getLayoutParams();
        containerExpendAnim.addUpdateListener(animation -> {
            containerParams.height = (int) animation.getAnimatedValue();
            container.setLayoutParams(containerParams);
        });
        containerExpendAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                showBottomAnim();
            }
        });
        containerExpendAnim.start();
    }

    /**
     * 展示雷达扩展动画
     */
    public void showRippleAnim() {
        rippleView.setVisibility(VISIBLE);
        rippleView.startAnim();
    }

    /**
     * 隐藏雷达扩展动画
     */
    public void stopRippleAnim() {
        rippleView.stopAnim();
        rippleView.setVisibility(GONE);
    }

    private void showBottomAnim() {
        bottomContainer.setVisibility(VISIBLE);
    }
}
