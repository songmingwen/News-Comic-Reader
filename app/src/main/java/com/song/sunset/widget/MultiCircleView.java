package com.song.sunset.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Song on 2016/12/3.
 */

public class MultiCircleView extends View {
    private static final float STROKE_WIDTH = 1F / 256F, // 描边宽度占比
            LINE_LENGTH = 3F / 64F, // 线段长度占比
            CRICLE_LARGER_RADIU = 3F / 64F,// 大圆半径
            CRICLE_SMALL_RADIU = 5F / 64F,// 小圆半径
            ARC_RADIU = 1F / 8F,// 弧半径
            ARC_TEXT_RADIU = 5F / 32F;// 弧围绕文字半径

    private Paint strokePaint;// 描边画笔

    private int size;// 控件边长

    private float strokeWidth;// 描边宽度
    private float ccX, ccY;// 中心圆圆心坐标
    private float largeCricleRadiu;// 大圆半径
    private float lineLength;// 线段长度

    public MultiCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔
        initPaint(context);
    }

    /**
     * 初始化画笔
     *
     * @param context Fuck
     */
    private void initPaint(Context context) {
        /*
         * 初始化描边画笔
         */
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 强制长宽一致
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取控件边长
        size = w;

        // 参数计算
        calculation();
    }

    /*
     * 参数计算
     */
    private void calculation() {
        // 计算描边宽度
        strokeWidth = STROKE_WIDTH * size;

        // 计算大圆半径
        largeCricleRadiu = size * CRICLE_LARGER_RADIU;

        // 计算线段长度
        lineLength = size * LINE_LENGTH;

        // 计算中心圆圆心坐标
        ccX = size / 2;
        ccY = size / 2;

        // 设置参数
        setPara();
    }

    /**
     * 设置参数
     */
    private void setPara() {
        // 设置描边宽度
        strokePaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制背景
        canvas.drawColor(0xFFF29B76);

        // 绘制中心圆
        canvas.drawCircle(ccX, ccY, largeCricleRadiu, strokePaint);

        // 绘制左上方图形
        drawTopLeft(canvas, -30);
        drawTopLeft(canvas, 30);
        drawTopLeft(canvas, 90);
        drawTopLeft(canvas, 150);
        drawTopLeft(canvas, -90);
        drawTopLeft(canvas, -150);
    }

    /**
     * 绘制左上方图形
     *
     * @param canvas
     * @param degrees
     */
    private void drawTopLeft(Canvas canvas, int degrees) {
        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-degrees);

        // 依次画：线-圈-线-圈
        canvas.drawLine(0, -largeCricleRadiu, 0, -lineLength * 2, strokePaint);
        canvas.drawCircle(0, -lineLength * 3, largeCricleRadiu, strokePaint);
        canvas.drawLine(0, -largeCricleRadiu * 4, 0, -lineLength * 5, strokePaint);
        canvas.drawCircle(0, -lineLength * 6, largeCricleRadiu, strokePaint);

        // 释放画布
        canvas.restore();
    }
}
