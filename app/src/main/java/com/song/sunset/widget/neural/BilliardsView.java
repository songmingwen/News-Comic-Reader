package com.song.sunset.widget.neural;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.song.sunset.R;

import androidx.annotation.Nullable;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/12
 */
public class BilliardsView extends View implements View.OnTouchListener {

    private Paint mDotPaint;

    /**
     * 控件宽度
     */
    private int mWidth;

    /**
     * 控件高度
     */
    private int mHeight;

    private NeuralNetWorksModel mNeuralNetWorksModel;

    private GestureDetector mGestureDetector;

    public BilliardsView(Context context) {
        this(context, null);
    }

    public BilliardsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BilliardsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BilliardsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();

    }

    private void init() {
        mNeuralNetWorksModel = new NeuralNetWorksModel();

        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mDotPaint.setColor(getContext().getResources().getColor(R.color.white));

        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
        setOnTouchListener(this);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mNeuralNetWorksModel.addDotToList(mWidth, mHeight, e.getX(), e.getY(), 0, 0, 2);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("song_onFling: ", velocityX + " --- " + velocityY);
            mNeuralNetWorksModel.addDotToList(mWidth, mHeight, e2.getX(), e2.getY(), velocityX / 100, velocityY / 100, 2);
            return true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mWidth = w - getPaddingLeft() - getPaddingRight();
        mHeight = h - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mNeuralNetWorksModel.getLinesList() == null || mNeuralNetWorksModel.getLinesList().isEmpty()) {
            invalidate();
        }

        drawDots(canvas);

        drawLines(canvas);

        mNeuralNetWorksModel.next(128);

        invalidate();

    }

    private void drawDots(Canvas canvas) {
        for (Dot dot : mNeuralNetWorksModel.getDotsList()) {
            mDotPaint.setAlpha(dot.getAlpha());
            canvas.drawCircle(dot.getX() + getPaddingLeft(), dot.getY() + getPaddingTop(), dot.getRadius(), mDotPaint);
        }
    }

    private void drawLines(Canvas canvas) {
        for (Line line : mNeuralNetWorksModel.getLinesList()) {
            mDotPaint.setAlpha(line.getAlpha());
            canvas.drawLine(line.getStartX() + getPaddingLeft(), line.getStartY() + getPaddingTop(),
                    line.getStopX() + getPaddingLeft(), line.getStopY() + getPaddingTop(), mDotPaint);
        }
    }
}
