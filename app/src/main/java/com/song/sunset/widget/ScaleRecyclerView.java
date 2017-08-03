package com.song.sunset.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

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

    public static final int DOUBLE_TAG_TIME_INTERVAL = 300;
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

    //    private GestureDetector detector;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector mGestureDetector;

    public enum LAYOUT_MANAGER_TYPE {
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
//        detector = new GestureDetector(context, this);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new ScaleRecyclerViewGestureListener());
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        if (detector != null) {
//            detector.onTouchEvent(event);
//        }
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        if (scaleGestureDetector != null) {
            scaleGestureDetector.onTouchEvent(event);
        }
        return false;
    }

    private long lastTapTime;
    private boolean isZooming = false;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;

    public class ScaleRecyclerViewGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            downX = e.getRawX();
            downY = e.getRawY();
            return false;
        }

//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
////        Log.i(TAG, "onSingleTapUp: " + e.getX());
//            if (System.currentTimeMillis() - lastTapTime < DOUBLE_TAG_TIME_INTERVAL) {
//                if (isZooming) {
//                    return false;
//                } else {
//                    if (getScaleX() == ORIGINAL_RATE) {
//                        zoom(ORIGINAL_RATE, MAX_SCALE_RATE, 0, (HALF_SCREEN_WIDTH - e.getX()) * (MAX_SCALE_RATE - 1), 0, (HALF_SCREEN_HEIGHT - e.getY()) * (MAX_SCALE_RATE - 1));
//                    } else {
//                        zoom(currentScale, ORIGINAL_RATE, getX(), 0, getY(), 0);
//                    }
//                }
//            }
//            lastTapTime = System.currentTimeMillis();
//            return false;
//        }

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
