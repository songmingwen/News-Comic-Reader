package com.song.sunset.utils;

import android.graphics.Color;
import androidx.annotation.ColorInt;

/**
 * @author songmingwen
 * @description
 * @since 2018/11/2
 */
public class ColorUtils {
    /**
     * 移除颜色的透明
     *
     * @param color 带透明的颜色
     * @return 去除透明的颜色
     */
    public static int removeAlpha(int color) {
        return 0xFF000000 | color;
    }

    /**
     * 给颜色设置透明度
     *
     * @param alpha 透明度，最大255
     * @return 设置好指定透明度的颜色
     */
    public static int setAlpha(int color, int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * 给颜色设置透明度
     *
     * @param factor 透明度，最大 1
     * @return 设置好指定透明度的颜色
     */
    public static int adjustAlpha(int color, float factor) {
        return setAlpha(color, Math.round(Color.alpha(color) * factor));
    }

    /**
     * 合并两个颜色
     *
     * @param backgroundColor 背景色
     * @param foregroundColor 前景色
     * @return 合并之后的颜色
     */
    public static int mergeColors(int backgroundColor, int foregroundColor) {
        final byte ALPHA_CHANNEL = 24;
        final byte RED_CHANNEL = 16;
        final byte GREEN_CHANNEL = 8;
        final byte BLUE_CHANNEL = 0;

        final double ap1 = (double) (backgroundColor >> ALPHA_CHANNEL & 0xff) / 255d;
        final double ap2 = (double) (foregroundColor >> ALPHA_CHANNEL & 0xff) / 255d;
        final double ap = ap2 + (ap1 * (1 - ap2));

        final double amount1 = (ap1 * (1 - ap2)) / ap;
        final double amount2 = amount1 / ap;

        int a = ((int) (ap * 255d)) & 0xff;

        int r = ((int) (((float) (backgroundColor >> RED_CHANNEL & 0xff) * amount1) + ((float) (foregroundColor >>
                RED_CHANNEL & 0xff) * amount2))) & 0xff;
        int g = ((int) (((float) (backgroundColor >> GREEN_CHANNEL & 0xff) * amount1) + ((float) (foregroundColor >>
                GREEN_CHANNEL & 0xff) * amount2))) & 0xff;
        int b = ((int) (((float) (backgroundColor & 0xff) * amount1) + ((float) (foregroundColor & 0xff) * amount2)))
                & 0xff;

        return a << ALPHA_CHANNEL | r << RED_CHANNEL | g << GREEN_CHANNEL | b << BLUE_CHANNEL;
    }

    /**
     * 获取 StatusBar 颜色
     *
     * @param baseColor 参考颜色
     * @return 参考颜色混合 20% 的黑色
     */
    @ColorInt
    public static int getStatusBarColor(@ColorInt int baseColor) {
        return ColorUtils.mergeColors(baseColor, ColorUtils.setAlpha(Color.BLACK, (int) (0.2f * 255)));
    }
}
