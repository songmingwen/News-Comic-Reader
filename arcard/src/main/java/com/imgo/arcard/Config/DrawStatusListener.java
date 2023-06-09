package com.imgo.arcard.Config;

/**
 * Desc:    模型识别状态监听
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/19 14:55
 */
public interface DrawStatusListener {
    /**
     * 识别到一个模型，并且正常绘制
     * @param canvasId 唯一标识
     */
    void onDrawing(String canvasId);

    /**
     * 未识别到模型
     */
    void onInvisible();
}
