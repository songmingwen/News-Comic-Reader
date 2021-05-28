package com.song.neural;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/4
 */
class Line {

    private float startX;

    private float startY;

    private float stopX;

    private float stopY;

    private float connectionThreshold;

    Line(float startX, float startY, float stopX, float stopY, float connection_threshold) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        connectionThreshold = connection_threshold;
    }

    float getStartX() {
        return startX;
    }

    float getStartY() {
        return startY;
    }

    float getStopX() {
        return stopX;
    }

    float getStopY() {
        return stopY;
    }

    int getAlpha() {
        double length = Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2));
        return (int) (255 * Math.sqrt(1 - (length / connectionThreshold)));
    }

}
