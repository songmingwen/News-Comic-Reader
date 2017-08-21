package com.song.sunset.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Song on 2017/8/18 0018.
 * E-mail: z53520@qq.com
 */

public class TouchFollowView extends View {

    private Paint mPaint;
    private Paint mOutPaint;
    private int mWidth;
    private int mHeight;

    public TouchFollowView(Context context) {
        this(context, null);
    }

    public TouchFollowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchFollowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0x772bbad8);
        mOutPaint.setColor(0x77ffa200);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFF000000);

        int radius = Math.min(mWidth, mHeight) / 4;
        int inRadius = Math.min(mWidth, mHeight) / 4;
        int outRadius = Math.min(mWidth, mHeight) / 12;
//        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaint);

        // 锁定画布
        canvas.save();

        float transX = endX - startX;
        float transY = endY - startY;

        int distance = (int) Math.sqrt(transX * transX + transY * transY);

        float inX = transX;
        float inY = transY;
        float outX = transX;
        float outY = transY;

        if (distance >= inRadius) {
            inX = inRadius * transX / distance;
            inY = inRadius * transY / distance;
        }

        if (distance >= outRadius) {
            outX = outRadius * transX / distance;
            outY = outRadius * transY / distance;
        }

        //移动画布使原始的圆心位于(0,0)的位置
        canvas.translate(mWidth / 2, mHeight / 2);

        canvas.drawCircle(outX, outY, radius * 1.5f, mOutPaint);
        canvas.drawCircle(inX, inY, radius * 0.6f, mPaint);

        // 释放画布
        canvas.restore();
    }

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = event.getRawX();
                endY = event.getRawY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                startX = startY = endX = endY = 0;
                invalidate();
                break;
        }
        return true;
    }
}
