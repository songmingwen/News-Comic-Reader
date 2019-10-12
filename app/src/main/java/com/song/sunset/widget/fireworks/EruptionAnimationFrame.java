package com.song.sunset.widget.fireworks;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songmingwen
 * @description 烟花特效
 * @since 2018/10/16
 */
public class EruptionAnimationFrame extends BaseAnimationFrame {

    private int elementSize;

    private static final int BASE_SPEED = 1100;
    private static final int RANDOM_SPEED = 900;

    public EruptionAnimationFrame(int elementSize, long duration) {
        super(duration);
        this.elementSize = elementSize;
    }

    @Override
    public void prepare(int x, int y, BitmapProvider.Provider bitmapProvider) {
        reset();
        setStartPoint(x, y);
        elements = generatedElements(x, y, bitmapProvider);
    }

    /**
     * 生成N个Element
     */
    protected List<Element> generatedElements(int x, int y, BitmapProvider.Provider bitmapProvider) {
        List<Element> elements = new ArrayList<>(elementSize);
        for (int i = 0; i < elementSize; i++) {

            int upCount = (int) (elementSize * 0.7);//向上抛的占 70%

            double startAngle,speed;
            if (i <= upCount) {
                startAngle = Math.random() * 180;
            } else {
                startAngle = 210 + Math.random() * 120;
            }
            speed = BASE_SPEED + Math.random() * RANDOM_SPEED;

            Element element = new EruptionElement(startAngle, speed, bitmapProvider.getBitmapByIndex(i));
            elements.add(element);
        }
        return elements;
    }

    public static class EruptionElement implements Element {
        private int x;
        private int y;
        private double angle;
        private double speed;
        /**
         * 重力加速度px/s
         */
        private static final float GRAVITY = 3000;
        private Bitmap bitmap;

        public EruptionElement(double angle, double speed, Bitmap bitmap) {
            this.angle = angle;
            this.speed = speed;
            this.bitmap = bitmap;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public Bitmap getBitmap() {
            return bitmap;
        }

        @Override
        public void evaluate(int start_x, int start_y, double time) {
            time = time / 1000;
            double x_speed = speed * Math.cos(angle * Math.PI / 180);
            double y_speed = -speed * Math.sin(angle * Math.PI / 180);
            x = (int) (start_x + (x_speed * time) - (bitmap.getWidth() / 2));
            y = (int) (start_y + (y_speed * time) + (GRAVITY * time * time) / 2 - (bitmap.getHeight() / 2));
        }
    }
}
