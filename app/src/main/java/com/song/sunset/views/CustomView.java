package com.song.sunset.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.song.sunset.R;
import com.song.sunset.utils.BitmapUtil;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by songmw3 on 2016/12/5.
 */

public class CustomView extends View {
    private Bitmap mBitmap;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int screenW = ViewUtil.getScreenWidth();

        mBitmap = BitmapUtil.getSmallBitmap(getResources(), R.drawable.ness, screenW);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, screenW, screenW * mBitmap.getHeight() / mBitmap.getWidth(), true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 声明一个临时变量来存储计算出的测量值
        int resultWidth;

        // 获取宽度测量规格中的mode
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        // 获取宽度测量规格中的size
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        /**
         * 如果爹心里有数
         */
        if (modeWidth == MeasureSpec.EXACTLY) {
            // 那么儿子也不要让爹难做就取爹给的大小吧
            resultWidth = sizeWidth;
        }
        /**
         * 如果爹心里没数
         */
        else {
            // 那么儿子可要自己看看自己需要多大了
            resultWidth = mBitmap.getWidth() + getPaddingLeft() + getPaddingRight();

            /**
             * 如果爹给儿子的是一个限制值
             */
            if (modeWidth == MeasureSpec.AT_MOST) {
                // 那么儿子自己的需求就要跟爹的限制比比看谁小要谁
                resultWidth = Math.min(resultWidth, sizeWidth);
            }
        }

        int resultHeight;
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (modeHeight == MeasureSpec.EXACTLY) {
            resultHeight = sizeHeight;
        } else {
            resultHeight = mBitmap.getHeight() + getPaddingTop() + getPaddingBottom();
            if (modeHeight == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, sizeHeight);
            }
        }

        // 设置测量尺寸
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, getPaddingLeft(), getPaddingTop(), null);
    }
}
