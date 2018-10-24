package com.song.sunset.widget.fireworks;

import android.graphics.Bitmap;

/**
 * @author songmingwen
 * @description 烟花特效
 * @since 2018/10/16
 */
public interface Element {

    int getX();

    int getY();

    Bitmap getBitmap();

    void evaluate(int start_x, int start_y, double time);

}
