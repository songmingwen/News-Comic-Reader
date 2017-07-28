package com.song.sunset.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by Song on 2016/9/6 0006.
 * Email:z53520@qq.com
 */
public class ShadowView extends View {
    private static final int RECT_SIZE = 800;// 方形大小
    private Paint mPaint;// 画笔

    private int left, top, right, bottom;// 绘制时坐标

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // setShadowLayer不支持HW
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        // 初始化画笔
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShadowLayer(ViewUtil.dip2px(4), 0, 0, Color.DKGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        left = ViewUtil.dip2px(5);
        top = ViewUtil.dip2px(5);
        right = canvas.getWidth() - ViewUtil.dip2px(5);
        bottom = canvas.getHeight() - ViewUtil.dip2px(5);
        // 先绘制位图
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
