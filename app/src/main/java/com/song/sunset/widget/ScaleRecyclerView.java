package com.song.sunset.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.video.SimplePlayer;

/**
 * Created by Song on 2016/8/31 0031.
 * Email:z53520@qq.com
 */
public class ScaleRecyclerView extends RecyclerView implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    private Context context;
    public static final String TAG = "ScaleRecyclerView";
    public static final int HALF_SCREEN_WIDTH = ViewUtil.getScreenWidth() / 2;
    public static final int HALF_SCREEN_HEIGHT = ViewUtil.getScreenHeigth() / 2;

    public static final int ANIMATOR_DURATION_TIME = 300;
    public static final float ORIGINAL_RATE = 1f;
    public static final float MAX_SCALE_RATE = 2.5f;
    public static final float MIN_SCALE_RATE = ORIGINAL_RATE;

    private float currentScale = ORIGINAL_RATE;
    private boolean atLastPosition = false;
    private boolean atFirstPosition = false;

    private int[] lastPositions;
    private int firstVisibleItemPosition;
    private int lastVisibleItemPosition;

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector mGestureDetector;

    private OnSingleTapListener mOnSingleTapListener;

    public interface OnSingleTapListener {
        void onSingleTap();
    }

    public void setOnSingleTapListener(OnSingleTapListener onSingleTapListener) {
        mOnSingleTapListener = onSingleTapListener;
    }

    private enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private LAYOUT_MANAGER_TYPE layoutManagerType;

    public ScaleRecyclerView(Context context) {
        this(context, null);
    }

    public ScaleRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ScaleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new ScaleRecyclerViewGestureListener());
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        if (scaleGestureDetector != null) {
            scaleGestureDetector.onTouchEvent(event);
        }
        return false;
    }

    private boolean isZooming = false;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;
    private FlingRunnable mFlingRunnable;

    private class ScaleRecyclerViewGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            downX = e.getRawX();
            downY = e.getRawY();
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mOnSingleTapListener != null) {
                mOnSingleTapListener.onSingleTap();
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isZooming) {
                return super.onDoubleTap(e);
            } else {
                if (getScaleX() == ORIGINAL_RATE) {
                    zoom(ORIGINAL_RATE, MAX_SCALE_RATE, 0, (HALF_SCREEN_WIDTH - e.getX()) * (MAX_SCALE_RATE - 1), 0, (HALF_SCREEN_HEIGHT - e.getY()) * (MAX_SCALE_RATE - 1));
                } else {
                    zoom(currentScale, ORIGINAL_RATE, getX(), 0, getY(), 0);
                }
                return true;
            }
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (getScaleX() != ORIGINAL_RATE) {
                moveX = e2.getRawX();
//            Log.i(TAG, "onScroll:moveX= " + moveX);
                float dx = moveX - downX;
                float positionX = dx + getX();
//            Log.i(TAG, "onScroll:=positionX " + positionX);
                setX(getPositionX(positionX));
                downX = moveX;

                if (atFirstPosition || atLastPosition) {
                    moveY = e2.getRawY();
                    float dy = moveY - downY;
                    float positionY = dy + getY();
                    setY(getPositionY(positionY));
                    downY = moveY;
                }
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mFlingRunnable = new FlingRunnable(getContext());
            //调用fling方法，传入控件宽高和当前x和y轴方向的速度
            //这里得到的vX和vY和scroller需要的velocityX和velocityY的负号正好相反
            //所以传入一个负值
            mFlingRunnable.fling(getWidth(), getHeight(), (int) velocityX, (int) velocityY);
            //执行run方法
            post(mFlingRunnable);
            return true;
        }
    }


    /**
     * 惯性滑动
     */
    private class FlingRunnable implements Runnable {
        private Scroller mScroller;
        private int mCurrentX, mCurrentY;

        public FlingRunnable(Context context) {
            mScroller = new Scroller(context);
        }

        public void cancelFling() {
            mScroller.forceFinished(true);
        }

        /**
         * 这个方法主要是从onTouch中或得到当前滑动的水平和竖直方向的速度
         * 调用scroller.fling方法，这个方法内部能够自动计算惯性滑动
         * 的x和y的变化率，根据这个变化率我们就可以对图片进行平移了
         */
        public void fling(int viewWidth, int viewHeight, int velocityX, int velocityY) {
            //startX为当前图片左边界的x坐标
            final int startX = (int) getX();
            final int minX, maxX, minY, maxY;
            //如果图片宽度大于控件宽度
            if (currentScale > 1) {
                //这是一个滑动范围[minX,maxX]，详情见下图
                minX = (int) (-ViewUtil.getScreenWidth() * (currentScale - 1)) / 2;
                maxX = (int) (ViewUtil.getScreenWidth() * (currentScale - 1)) / 2;
            } else {
                //如果图片宽度小于控件宽度，则不允许滑动
                minX = maxX = startX;
            }

            //如果图片高度大于控件高度，同理
            final int startY = (int) getY();
//            if (currentScale > 1) {
//                minY = 0;
//                maxY = (int) (ViewUtil.getScreenHeigth() * (currentScale - 1));
//            } else {
            minY = maxY = startY;
//            }
            mCurrentX = startX;
            mCurrentY = startY;

            if (startX != maxX /*|| startY != maxY*/) {
                //调用fling方法，然后我们可以通过调用getCurX和getCurY来获得当前的x和y坐标
                //这个坐标的计算是模拟一个惯性滑动来计算出来的，我们根据这个x和y的变化可以模拟
                //出图片的惯性滑动
                mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            }

        }

        /**
         * 每隔16ms调用这个方法，实现惯性滑动的动画效果
         */
        @Override
        public void run() {
            if (mScroller.isFinished()) {
                return;
            }
            //如果返回true，说明当前的动画还没有结束，我们可以获得当前的x和y的值
            if (mScroller.computeScrollOffset()) {
                //获得当前的x坐标
                final int newX = mScroller.getCurrX();
                //获得当前的y坐标
                final int newY = mScroller.getCurrY();
                //进行平移操作
//                postTranslate(mCurrentX - newX, mCurrentY - newY);
//                checkBorderWhenTranslate();
//                setImageMatrix(mScaleMatrix);

                setX(getPositionX(newX));

                mCurrentX = newX;
                mCurrentY = newY;
                //每16ms调用一次
                postDelayed(this, 16);
            }
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used.");
            }
        }
        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
//        Log.i("onScrollStateChanged", "visibleItemCount" + visibleItemCount);
//        Log.i("onScrollStateChanged", "lastVisibleItemPosition" + lastVisibleItemPosition);
//        Log.i("onScrollStateChanged", "totalItemCount" + totalItemCount);
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1) {
            atLastPosition = true;
        } else {
            atLastPosition = false;
        }
        if (firstVisibleItemPosition == 0) {
            atFirstPosition = true;
        } else {
            atFirstPosition = false;
        }
    }

    /**
     * ScaleGestureDetector------------------------------------begin
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
//        Log.i(TAG, "onScale: " + detector.getScaleFactor() + "  ***  " + detector.getFocusX() + "  ***  " + detector.getFocusY());
        float scaleFactor = detector.getScaleFactor();
        if (Math.abs(scaleFactor - 1) < 0.001) {
            return true;
        }
        float nextScale = currentScale * scaleFactor;
        if (nextScale > MAX_SCALE_RATE) {
            nextScale = MAX_SCALE_RATE;
        }
        if (nextScale < MIN_SCALE_RATE) {
            nextScale = MIN_SCALE_RATE;
        }
        setScaleRate(nextScale);
        currentScale = nextScale;
        if (nextScale != MIN_SCALE_RATE) {
            setX(getPositionX(getX()));
            setY(getPositionY(getY()));
        } else {
            setX(0);
            setY(0);
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
//        Log.i(TAG, "onScaleBegin: ");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
//        Log.i(TAG, "onScaleEnd: ");
        if (getScaleX() < ORIGINAL_RATE) {
            zoom(currentScale, ORIGINAL_RATE, getX(), 0, getY(), 0);
        }
    }

    /**
     * ScaleGestureDetector------------------------------------end
     */

    private float getPositionX(float positionX) {
        float maxPositionX = HALF_SCREEN_WIDTH * (currentScale - 1);
        if (positionX > maxPositionX) {
            positionX = maxPositionX;
        }
        if (positionX < -maxPositionX) {
            positionX = -maxPositionX;
        }
        return positionX;
    }

    private float getPositionY(float positionY) {
        float maxPositionY = HALF_SCREEN_HEIGHT * (currentScale - 1);
        if (positionY > maxPositionY) {
            positionY = maxPositionY;
        }
        if (positionY < -maxPositionY) {
            positionY = -maxPositionY;
        }
        return positionY;
    }

    private void zoom(float fromRate, final float toRate, float fromX, float toX, float fromY, float toY) {
        isZooming = true;
        AnimatorSet animatorSet = new AnimatorSet();
        final ValueAnimator translationXAnimator = ValueAnimator.ofFloat(fromX, toX);
        translationXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setX((Float) animation.getAnimatedValue());
            }
        });

        ValueAnimator translationYAnimator = ValueAnimator.ofFloat(fromY, toY);
        translationYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setY((Float) animation.getAnimatedValue());
            }
        });

        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(fromRate, toRate);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setScaleRate((float) animation.getAnimatedValue());
            }
        });
        animatorSet.playTogether(translationXAnimator, translationYAnimator, scaleAnimator);
        animatorSet.setDuration(ANIMATOR_DURATION_TIME);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isZooming = false;
                currentScale = toRate;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void setScaleRate(float rate) {
        setScaleX(rate);
        setScaleY(rate);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}