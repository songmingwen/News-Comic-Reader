package com.song.sunset.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import com.song.sunset.R;

/**
 * Created by Song on 2017/7/26 0026.
 * E-mail: z53520@qq.com
 */

public class GoodsTag extends RelativeLayout implements View.OnClickListener {

    private TextView mLeftText, mRightText;
    private ImageView dot, dotBack;
    private LinearLayout content;
    private int mPosition;
    private Context mContext;
    private AnimatorSet mAnimatorSet;

    public GoodsTag(Context context) {
        this(context, null);
    }

    public GoodsTag(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsTag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_slide_goos_tags, this);
        content = (LinearLayout) findViewById(R.id.ll_goods_content);
        mLeftText = (TextView) findViewById(R.id.text_slide_goods_left);
        mRightText = (TextView) findViewById(R.id.text_slide_goods_right);
        dot = (ImageView) findViewById(R.id.image_dot);
        dotBack = (ImageView) findViewById(R.id.image_dot_back);
        content.setOnClickListener(this);
        initAnimator();
    }

    public void setData(String goodBean, int position) {
        this.mPosition = position;
        if (goodBean == null) {
            return;
        }
        mLeftText.setText(TextUtils.isEmpty(goodBean) ? "查看价格" : "￥" + goodBean);
        mRightText.setText(TextUtils.isEmpty(goodBean) ? "查看价格" : "￥" + goodBean);
        addAnimListener();
        playDot();
    }

    @Override
    public void onClick(View v) {

    }

    public void showLeftText(boolean showLeft) {
        if (mLeftText != null && mRightText != null) {
            mLeftText.setVisibility(showLeft ? VISIBLE : GONE);
            mRightText.setVisibility(showLeft ? GONE : VISIBLE);
        }
    }

    private void initAnimator() {
        AnimatorSet dotSet = new AnimatorSet();
        ObjectAnimator scaleDotX = ObjectAnimator.ofFloat(dot, "scaleX", 1f, 1f, 1f, 1f, 0.7f, 1.3f, 1f);
        ObjectAnimator scaleDotY = ObjectAnimator.ofFloat(dot, "scaleY", 1f, 1f, 1f, 1f, 0.7f, 1.3f, 1f);
        scaleDotX.setDuration(1200);
        scaleDotY.setDuration(1200);
        dotSet.setInterpolator(new AccelerateDecelerateInterpolator());
        dotSet.playTogether(scaleDotX, scaleDotY);

        AnimatorSet backDotSet = new AnimatorSet();
        ObjectAnimator scaleBackDotX = ObjectAnimator.ofFloat(dotBack, "scaleX", 1f, 4f);
        ObjectAnimator scaleBackDotY = ObjectAnimator.ofFloat(dotBack, "scaleY", 1f, 4f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(dotBack, "alpha", 1.0f, 0.0f);
        scaleBackDotX.setRepeatCount(1);
        scaleBackDotY.setRepeatCount(1);
        alpha.setRepeatCount(1);
        scaleBackDotX.setDuration(600);
        scaleBackDotY.setDuration(600);
        alpha.setDuration(600);
        backDotSet.setInterpolator(new AccelerateDecelerateInterpolator());
        backDotSet.playTogether(scaleBackDotX, scaleBackDotY, alpha);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(dotSet, backDotSet);
    }

    /**
     * 添加动画监听，动画结束重播动画
     */
    private void addAnimListener() {
        if (mAnimatorSet == null) {
            return;
        }
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                playDot();
            }
        });
    }

    public void stopAnim() {
        if (mAnimatorSet != null) {
            mAnimatorSet.removeAllListeners();
        }
    }

    /**
     * 开始动画，如果监听到退出图集，那么停止动画。
     */
    private void playDot() {
        if (mContext instanceof Activity) {
            Activity slideActivity = (Activity) mContext;
            if (slideActivity.isFinishing() || mAnimatorSet == null) {
                stopAnim();
                mAnimatorSet = null;
                mContext = null;
                return;
            }
        }
        Log.i("song", "playDot: ");
        mAnimatorSet.start();
    }
}
