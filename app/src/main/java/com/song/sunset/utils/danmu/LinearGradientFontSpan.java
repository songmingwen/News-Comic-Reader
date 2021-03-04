package com.song.sunset.utils.danmu;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     Desc   : 
 *     Author : songmingwen
 *     Time   : 2021/3/4 15:28
 * </pre>
 */
public class LinearGradientFontSpan extends ReplacementSpan {

    private int startColor;

    private int endColor;

    private int repeatLength = NO_LENGTH;

    private int orientation;

    public static final int VERTICAL = 1;

    public static final int HORIZONTAL = 0;

    public static final int NO_LENGTH = -1;

    private LinearGradient mLg;

    private LinearGradientFontSpan(){}

    public LinearGradientFontSpan(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        orientation = HORIZONTAL;
        repeatLength = NO_LENGTH;

        //从上到下渐变
        mLg = getLg();
    }

    @NotNull
    private LinearGradient getLg() {
        if (orientation == HORIZONTAL) {
            return new LinearGradient(0, 0, repeatLength, 0,
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT);
        } else {
            return new LinearGradient(0, 0, 0, repeatLength,
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT);
        }
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        mLg = getLg();
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
        mLg = getLg();
    }

    public void setRepeatLength(int repeatLength) {
        this.repeatLength = repeatLength;
        mLg = getLg();
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        mLg = getLg();
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        int size = (int) paint.measureText(text, start, end);
        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        if (fm != null) {
            fm.top = metrics.top;
            fm.ascent = metrics.ascent;
            fm.descent = metrics.descent;
            fm.bottom = metrics.bottom;
        }
        if (repeatLength == NO_LENGTH) {
            repeatLength = size;
            mLg = getLg();
        }
        return size;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        paint.setShader(mLg);
        canvas.drawText(text, start, end, x, y, paint);//绘制文字
    }
}