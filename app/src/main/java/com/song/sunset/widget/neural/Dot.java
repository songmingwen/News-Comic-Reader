package com.song.sunset.widget.neural;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/3
 */
class Dot {

    private static final float MIN_DD = 0.1f;

    private static final float SCALE_RATE = 0.5f;

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
            x = width - (tempX - width);
            dX = -dX;
        } else if (tempX < 0) {
            x = -tempX;
            dX = -dX;
        } else {
            x = tempX;
        }

        float tempY = y + dY;
        if (tempY > height) {
            y = height - (tempY - height);
            dY = -dY;
        } else if (tempY < 0) {
            y = -tempY;
            dY = -dY;
        } else {
            y = tempY;
        }

        lineAmount = 0;

    }
}
