package com.song.sunset.widget.neural;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.song.sunset.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/3
 */
public class NeuralNetWorksView extends View {

    private static final int DEFAULT_ELEMENT_AMOUNT = 64;

    private static final int DEFAULT_CONNECTION_THRESHOLD = 512;

    private static final int DEFAULT_SPEED = 4;

    private static final int DEFAULT_DOT_RADIUS = 4;

    private int DEFAULT_DOT_COLOR = getContext().getResources().getColor(R.color.Grey_500);

    private int DEFAULT_LINE_COLOR = getContext().getResources().getColor(R.color.Grey_500);

    private Paint mDotPaint;

    private Paint mLinePaint;

    /**
     * 连线阀值，小于此值时点与点之间会有连线
     */
    private int mConnectionThreshold;

    /**
     * 点的数量
     */
    private int mElementAmount;

    /**
     * 点的基础速度
     */
    private int mSpeed;

    /**
     * 圆点半径
     */
    private float mRadius;

    /**
     * 点的颜色
     */
    private int mDotColor;

    /**
     * 线的颜色
     */
    private int mLineColor;

    /**
     * 控件宽度
     */
    private int mWidth;

    /**
     * 控件高度
     */
    private int mHeight;

    private ArrayList<Dot> mDots;

    public NeuralNetWorksView(Context context) {
        this(context, null);
    }

    public NeuralNetWorksView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NeuralNetWorksView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NeuralNetWorksView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NeuralNetWorksView, defStyleAttr, defStyleRes);
        mElementAmount = a.getInteger(R.styleable.NeuralNetWorksView_neural_element_amount, DEFAULT_ELEMENT_AMOUNT);
        mConnectionThreshold = a.getInteger(R.styleable.NeuralNetWorksView_neural_connection_threshold, DEFAULT_CONNECTION_THRESHOLD);
        mSpeed = a.getInteger(R.styleable.NeuralNetWorksView_neural_speed, DEFAULT_SPEED);
        mRadius = a.getFloat(R.styleable.NeuralNetWorksView_neural_dot_radius, DEFAULT_DOT_RADIUS);
        mDotColor = a.getColor(R.styleable.NeuralNetWorksView_neural_dot_color, DEFAULT_DOT_COLOR);
        mLineColor = a.getColor(R.styleable.NeuralNetWorksView_neural_line_color, DEFAULT_LINE_COLOR);
        a.recycle();

        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mDotPaint.setColor(mDotColor);
        mLinePaint.setColor(mLineColor);

        mDots = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mWidth = w - getPaddingLeft() - getPaddingRight();
        mHeight = h - getPaddingTop() - getPaddingBottom();
        setDots();
    }

    private void setDots() {
        mDots.clear();
        mDots = NeuralNetWorksModel.getInstance().getDotList(mElementAmount, mWidth, mHeight, mSpeed, mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDots == null || mDots.isEmpty()) {
            return;
        }

        drawLines(canvas);

        drawDots(canvas);

        next();

        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NeuralNetWorksModel.getInstance().clear();
    }

    private void drawLines(Canvas canvas) {
        for (Line line : NeuralNetWorksModel.getInstance().getLinesList()) {
            mLinePaint.setAlpha(line.getAlpha());
            canvas.drawLine(line.getStartX() + getPaddingLeft(), line.getStartY() + getPaddingTop(),
                    line.getStopX() + getPaddingLeft(), line.getStopY() + getPaddingTop(), mLinePaint);
        }
    }

    private void drawDots(Canvas canvas) {
        for (Dot dot : mDots) {
            mDotPaint.setAlpha(dot.getAlpha());
            canvas.drawCircle(dot.getX() + getPaddingLeft(), dot.getY() + getPaddingTop(), dot.getRadius(), mDotPaint);
        }
    }

    private void next() {
        NeuralNetWorksModel.getInstance().next(mConnectionThreshold);
    }

    public void setConnectionThreshold(int connectionThreshold) {
        mConnectionThreshold = connectionThreshold;
    }

    public void setElementAmount(int elementAmount) {
        mElementAmount = elementAmount;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public void invalidateView() {
        setDots();
    }
}
