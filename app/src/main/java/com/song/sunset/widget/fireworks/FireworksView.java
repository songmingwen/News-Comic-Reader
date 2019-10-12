package com.song.sunset.widget.fireworks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;


import com.song.sunset.R;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author songmingwen
 * @description 烟花特效
 * @since 2018/10/16
 */

public class FireworksView extends View implements AnimationEndListener {

    private static final String TAG = "FireworksView";

    private static final int DEFAULT_INTERVAL = 16;
    private static final int MAX_FRAME_SIZE = 16;
    private static final int ERUPTION_ELEMENT_AMOUNT = 10;

    private AnimationFramePool animationFramePool;

    private AnimationHandler animationHandler;

    private BitmapProvider.Provider provider;

    private static int mInterval;

    public FireworksView(@NonNull Context context) {
        this(context, null);
    }

    public FireworksView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FireworksView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        animationHandler = new AnimationHandler(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FireworksView, defStyleAttr, 0);
        int elementAmount = a.getInteger(R.styleable.FireworksView_element_amount, ERUPTION_ELEMENT_AMOUNT);
        int maxFrameSize = a.getInteger(R.styleable.FireworksView_max_layer, MAX_FRAME_SIZE);
        mInterval = a.getInteger(R.styleable.FireworksView_interval_frame_rate, DEFAULT_INTERVAL);
        a.recycle();

        animationFramePool = new AnimationFramePool(maxFrameSize, elementAmount);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!animationFramePool.hasRunningAnimation())
            return;
        //遍历所有AnimationFrame 并绘制Element
        // note: 需要倒序遍历 nextFrame方法可能会改变runningFrameList Size 导致异常
        List<AnimationFrame> runningFrameList = animationFramePool.getRunningFrameList();
        for (int i = runningFrameList.size() - 1; i >= 0; i--) {
            AnimationFrame animationFrame = runningFrameList.get(i);
            List<Element> elementList = animationFrame.nextFrame(mInterval);
            for (Element element : elementList) {
                canvas.drawBitmap(element.getBitmap(), element.getX(), element.getY(), null);
            }
        }

    }


    public void launch(int x, int y) {
        AnimationFrame eruptionAnimationFrame = animationFramePool.obtain();
        if (eruptionAnimationFrame != null && !eruptionAnimationFrame.isRunning()) {
            eruptionAnimationFrame.setAnimationEndListener(this);
            eruptionAnimationFrame.prepare(x, y, getProvider());
        }
        animationHandler.removeMessages(AnimationHandler.MESSAGE_CODE_REFRESH_ANIMATION);
        animationHandler.sendEmptyMessageDelayed(AnimationHandler.MESSAGE_CODE_REFRESH_ANIMATION, mInterval);

    }

    public boolean hasAnimation() {
        return animationFramePool.hasRunningAnimation();
    }

    public void setProvider(BitmapProvider.Provider provider) {
        this.provider = provider;
    }

    public BitmapProvider.Provider getProvider() {
        if (provider == null) {
            provider = new BitmapProvider.Builder(getContext())
                    .build();
        }
        return provider;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!hasAnimation())
            return;
        // 回收所有动画 并暂停动画
        animationFramePool.recycleAll();

        animationHandler.removeMessages(AnimationHandler.MESSAGE_CODE_REFRESH_ANIMATION);
    }

    @Override
    public void onAnimationEnd(AnimationFrame animationFrame) {
        onRecycle(animationFrame);
    }

    /**
     * 回收SurpriseView  添加至空闲队列方便下次使用
     */
    private void onRecycle(AnimationFrame animationFrame) {
        animationFrame.reset();
        animationFramePool.recycle(animationFrame);
    }

    private static final class AnimationHandler extends Handler {
        public static final int MESSAGE_CODE_REFRESH_ANIMATION = 1200;
        private WeakReference<FireworksView> weakReference;

        public AnimationHandler(FireworksView superLikeLayout) {
            weakReference = new WeakReference<>(superLikeLayout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_CODE_REFRESH_ANIMATION && weakReference != null && weakReference.get() != null) {
                weakReference.get().invalidate();
                // 动画还未结束继续刷新
                if (weakReference.get().hasAnimation()) {
                    sendEmptyMessageDelayed(MESSAGE_CODE_REFRESH_ANIMATION, mInterval);
                }
            }

        }
    }

}
