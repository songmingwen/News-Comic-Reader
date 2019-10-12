package com.song.sunset.widget.fireworks;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songmingwen
 * @description 烟花特效
 * @since 2018/10/16
 */
public class AnimationFramePool {

    private static final int DURATION = 1000;
    private List<AnimationFrame> runningFrameList;
    private List<AnimationFrame> idleFrameList;


    private int maxFrameSize;
    private int elementAmount;
    private int frameCount;

    AnimationFramePool(int maxFrameSize, int elementAmount) {
        this.maxFrameSize = maxFrameSize;
        this.elementAmount = elementAmount;
        runningFrameList = new ArrayList<>(maxFrameSize);
        idleFrameList = new ArrayList<>(maxFrameSize);
    }

    boolean hasRunningAnimation() {
        return runningFrameList.size() > 0;
    }

    AnimationFrame obtain() {

        // RunningAnimationFrame 存在onlyOne直接复用
        AnimationFrame animationFrame = getRunningFrameListByOnlyOne();
        if (animationFrame != null)
            return animationFrame;

        // 有空闲AnimationFrame直接使用, 加入runningFrame队列中
        animationFrame = removeIdleFrameListDownByType();
        if (animationFrame == null && frameCount < maxFrameSize) {
            frameCount++;
            animationFrame = new EruptionAnimationFrame(elementAmount, DURATION);
        }
        if (animationFrame != null) {
            runningFrameList.add(animationFrame);
        }

        return animationFrame;
    }

    private AnimationFrame getRunningFrameListByOnlyOne() {
        for (int i = runningFrameList.size() - 1; i >= 0; i--) {
            AnimationFrame animationFrame = runningFrameList.get(i);
            if (animationFrame.onlyOne())
                return animationFrame;
        }
        return null;
    }

    void recycleAll() {
        for (int i = runningFrameList.size() - 1; i >= 0; i--) {
            AnimationFrame animationFrame = runningFrameList.get(i);
            recycle(animationFrame);
            animationFrame.reset();
        }
    }

    void recycle(AnimationFrame animationFrame) {
        runningFrameList.remove(animationFrame);
        idleFrameList.add(animationFrame);
    }

    List<AnimationFrame> getRunningFrameList() {
        return runningFrameList;
    }

    private AnimationFrame removeIdleFrameListDownByType() {
        for (int i = idleFrameList.size() - 1; i >= 0; i--) {
            return idleFrameList.remove(i);
        }
        return null;
    }
}
