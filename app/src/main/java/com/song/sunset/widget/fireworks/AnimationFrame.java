package com.song.sunset.widget.fireworks;

import java.util.List;

/**
 * @author songmingwen
 * @description 烟花特效
 * @since 2018/10/16
 */
public interface AnimationFrame {

    boolean isRunning();

    List<Element> nextFrame(long interval);

    boolean onlyOne();

    void setAnimationEndListener(AnimationEndListener animationEndListener);

    void reset();

    void prepare(int x, int y, BitmapProvider.Provider bitmapProvider);

}
