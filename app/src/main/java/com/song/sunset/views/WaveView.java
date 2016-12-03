package com.song.sunset.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by songmw3 on 2016/12/3.
 */

public class WaveView extends View {

    private int vWidth, vHeight;// 控件宽高

    private Path wave_1_mPath;// 路径对象
    private Paint wave_1_mPaint;// 画笔对象

    private Path wave_2_mPath;// 路径对象
    private Paint wave_2_mPaint;// 画笔对象

    public static final int WAVE_1_SPEED = 3;//浪的速度
    public static final int WAVE_2_SPEED = 2;//浪的速度

    private float wave_1_ctrX_1, wave_1_ctrY_1;// 控制点1的xy坐标
    private float wave_1_ctrX_2, wave_1_ctrY_2;// 控制点2的xy坐标

    private float wave_2_ctrX_1, wave_2_ctrY_1;// 控制点1的xy坐标
    private float wave_2_ctrX_2, wave_2_ctrY_2;// 控制点2的xy坐标

    private boolean wave_1_goTop_1 = true;// 判断控制点是该上移还是下移
    private boolean wave_1_goTop_2 = false;// 判断控制点是该上移还是下移
    private boolean wave_1_goLeft = false;// 判断控制点是该上移还是下移

    private boolean wave_2_goTop_1 = false;// 判断控制点是该上移还是下移
    private boolean wave_2_goTop_2 = true;// 判断控制点是该上移还是下移
    private boolean wave_2_goLeft = true;// 判断控制点是该上移还是下移

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 实例化画笔并设置参数
        wave_1_mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        wave_2_mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        wave_1_mPaint.setColor(0x772bbad8);
        wave_2_mPaint.setColor(0x992bbad8);

        // 实例化路径对象
        wave_1_mPath = new Path();
        wave_2_mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取控件宽高
        vWidth = w;
        vHeight = h;

        wave_1_ctrX_1 = vWidth / 4;
        wave_1_ctrY_1 = vHeight / 4;
        wave_1_ctrX_2 = vWidth / 4 * 3;
        wave_1_ctrY_2 = vHeight / 4 * 3;

        wave_2_ctrX_1 = vWidth / 4;
        wave_2_ctrY_1 = vHeight / 4 * 3;
        wave_2_ctrX_2 = vWidth / 4 * 3;
        wave_2_ctrY_2 = vHeight / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 设置Path起点
         * 注意我将Path的起点设置在了控件的外部看不到的区域
         * 如果我们将起点设置在控件左端x=0的位置会使得贝塞尔曲线变得生硬
         * 至于为什么刚才我已经说了
         * 所以我们稍微让起点往“外”走点
         */
        wave_1_mPath.moveTo(-1 / 4F * vWidth, vHeight / 2);
        wave_2_mPath.moveTo(-1 / 4F * vWidth, vHeight / 2);
        /**
         * 以二阶曲线的方式通过控制点连接位于控件右边的终点
         * 终点的位置也是在控件外部
         * 我们只需不断让ctrX的大小变化即可实现“浪”的效果
         */
        wave_1_mPath.cubicTo(wave_1_ctrX_1, wave_1_ctrY_1, wave_1_ctrX_2, wave_1_ctrY_2, vWidth + 1 / 4F * vWidth, vHeight / 2);
        wave_2_mPath.cubicTo(wave_2_ctrX_1, wave_2_ctrY_1, wave_2_ctrX_2, wave_2_ctrY_2, vWidth + 1 / 4F * vWidth, vHeight / 2);
        // 围绕控件闭合曲线
        wave_1_mPath.lineTo(vWidth + 1 / 4F * vWidth, vHeight);
        wave_2_mPath.lineTo(vWidth + 1 / 4F * vWidth, vHeight);
        wave_1_mPath.lineTo(-1 / 4F * vWidth, vHeight);
        wave_2_mPath.lineTo(-1 / 4F * vWidth, vHeight);
        wave_1_mPath.close();
        wave_2_mPath.close();

        canvas.drawPath(wave_1_mPath, wave_1_mPaint);
        canvas.drawPath(wave_2_mPath, wave_2_mPaint);
        /**第一个浪*/
        if (wave_1_ctrY_1 >= vHeight * 3 / 2) {
            wave_1_goTop_1 = true;
        } else if (wave_1_ctrY_1 <= -vHeight / 2) {
            wave_1_goTop_1 = false;
        }
        wave_1_ctrY_1 = wave_1_goTop_1 ? wave_1_ctrY_1 - WAVE_1_SPEED : wave_1_ctrY_1 + WAVE_1_SPEED;

        if (wave_1_ctrY_2 >= vHeight * 3 / 2) {
            wave_1_goTop_2 = true;
        } else if (wave_1_ctrY_2 <= -vHeight / 2) {
            wave_1_goTop_2 = false;
        }
        wave_1_ctrY_2 = wave_1_goTop_2 ? wave_1_ctrY_2 - WAVE_1_SPEED : wave_1_ctrY_2 + WAVE_1_SPEED;

        if (wave_1_ctrX_1 <= 0) {
            wave_1_goLeft = false;
        } else if (wave_1_ctrX_1 >= vWidth / 2) {
            wave_1_goLeft = true;
        }
        wave_1_ctrX_1 = wave_1_goLeft ? wave_1_ctrX_1 - WAVE_1_SPEED : wave_1_ctrX_1 + WAVE_1_SPEED;
        wave_1_ctrX_2 = wave_1_goLeft ? wave_1_ctrX_2 - WAVE_1_SPEED : wave_1_ctrX_2 + WAVE_1_SPEED;

        /**第二个浪*/
        if (wave_2_ctrY_1 >= vHeight * 3 / 2) {
            wave_2_goTop_1 = true;
        } else if (wave_2_ctrY_1 <= -vHeight / 2) {
            wave_2_goTop_1 = false;
        }
        wave_2_ctrY_1 = wave_2_goTop_1 ? wave_2_ctrY_1 - WAVE_2_SPEED : wave_2_ctrY_1 + WAVE_2_SPEED;

        if (wave_2_ctrY_2 >= vHeight * 3 / 2) {
            wave_2_goTop_2 = true;
        } else if (wave_2_ctrY_2 <= -vHeight / 2) {
            wave_2_goTop_2 = false;
        }
        wave_2_ctrY_2 = wave_2_goTop_2 ? wave_2_ctrY_2 - WAVE_2_SPEED : wave_2_ctrY_2 + WAVE_2_SPEED;

        if (wave_2_ctrX_1 <= 0) {
            wave_2_goLeft = false;
        } else if (wave_2_ctrX_1 >= vWidth / 2) {
            wave_2_goLeft = true;
        }
        wave_2_ctrX_1 = wave_2_goLeft ? wave_2_ctrX_1 - WAVE_2_SPEED : wave_2_ctrX_1 + WAVE_2_SPEED;
        wave_2_ctrX_2 = wave_2_goLeft ? wave_2_ctrX_2 - WAVE_2_SPEED : wave_2_ctrX_2 + WAVE_2_SPEED;

        wave_1_mPath.reset();
        wave_2_mPath.reset();
        // 重绘
        invalidate();
    }
}
