package com.song.sunset.widget.neural;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/3
 */
class Dot {

    private static final float MIN_DD = 0.1f;

    private static final float SCALE_RATE = 0.5f;

    private boolean hasGravitational = false;

    /**
     * 点的 x 坐标
     */
    private float x;

    /**
     * 点的 y 坐标
     */
    private float y;

    /**
     * 点 x 轴每帧位移距离
     */
    private float dX;

    /**
     * 点 y 轴每帧位移的距离
     */
    private float dY;

    /**
     * 点的半径
     */
    private float radius;

    private float width;

    private float height;

    private float lineAmount;

    private ArrayList<Dot> aroundDots = new ArrayList<>();

    Dot() {

    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getRadius() {
        float tempRadius = radius;
        if (lineAmount > 0) {
            tempRadius = (float) (tempRadius * Math.sqrt(1 + (lineAmount * SCALE_RATE)));
        }
        return tempRadius;
    }

    int getAlpha() {
        if (lineAmount == 0) {
            return 255;
        }
        double e = Math.pow((1 + (1 / lineAmount)), lineAmount);
        return (int) (255 * Math.sqrt(e / Math.E));
    }

    void addLineAmount() {
        lineAmount++;
    }

    private void clearLineAmount() {
        lineAmount = 0;
    }

    /**
     * @param width  view 的宽度
     * @param height view 的高度
     * @param dD     点位移距离的基础值
     * @param radius 圆点半径的基础值
     * @return 返回一个点
     */
    Dot obtain(float width, float height, float dD, float radius) {

        this.radius = radius;

        this.width = width;
        this.height = height;

        this.x = (float) (Math.random() * width);
        this.y = (float) (Math.random() * height);

        double dx = Math.max((Math.random() + MIN_DD) * dD, MIN_DD);
        this.dX = (float) (isPositive() ? dx : -dx);

        double dy = Math.max((Math.random() + MIN_DD) * dD, MIN_DD);
        this.dY = (float) (isPositive() ? dy : -dy);
        return this;

    }

    Dot obtain(float width, float height, float x, float y, float dX, float dY, float radius) {
        this.radius = radius;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.dX = dX;
        this.dY = dY;
        return this;
    }

    private boolean isPositive() {
        return Math.random() > 0.5;
    }

    void next() {

        float tempX = x + dX;

        if (tempX > width) {
            int roundX = (int) (tempX / width);
            float remainderX = tempX % width;
            if (roundX % 2 != 0) {
                x = width - remainderX;
                dX = -dX;
            } else {
                x = remainderX;
            }
        } else if (tempX < 0) {
            int roundX = (int) (-tempX / width);
            float remainderX = -tempX % width;
            if (roundX % 2 != 0) {
                x = width - remainderX;
            } else {
                dX = -dX;
                x = remainderX;
            }
        } else {
            x = tempX;
        }

        float tempY = y + dY;

        if (tempY > height) {
            int roundY = (int) (tempY / height);
            float remainderY = tempY % height;
            if (roundY % 2 != 0) {
                y = height - remainderY;
                dY = -dY;
            } else {
                y = remainderY;
            }
        } else if (tempY < 0) {
            int roundY = (int) (-tempY / height);
            float remainderY = -tempY % height;
            if (roundY % 2 != 0) {
                y = height - remainderY;
            } else {
                dY = -dY;
                y = remainderY;
            }
        } else {
            y = tempY;
        }

        clearLineAmount();

    }
}
