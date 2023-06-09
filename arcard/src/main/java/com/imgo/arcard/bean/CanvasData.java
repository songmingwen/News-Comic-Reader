package com.imgo.arcard.bean;

import java.util.Arrays;
import java.util.List;

/**
 * Desc:    画布数据，用于记录 ar 播视频相关的数据
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/19 10:31
 */
public class CanvasData {

    /**
     * 视频 model 类型
     */
    public static final String MODEL_TYPE_VIDEO = "video";

    /**
     * 位置信息，默认铺满全屏
     */
    public static final float[][] DEFAULT_VERTEX_BUFFER = {
            {0, 100, 0},
            {100, 100, 0},
            {100, 0, 0},
            {0, 0, 0}};

    /**
     * 画布名称、或画布ID，唯一标识
     */
    public String canvasName;

    /**
     * 本地特征文件路径(需要带上文件名，不需要带文件后缀)
     */
    public String localFeaturePath;

    /**
     * 模型名称（如视频名称）
     */
    public String modelName;

    /**
     * 模型类型（如视频类型）。
     * def：不传默认为视频
     */
    public String modelType;

    /**
     * 模型位置：三维位置数组，即矩形的四点。
     * def：不传默认铺满识别区域
     */
    public float[][] position;

    /**
     * 包含纹理路径的列表（如视频本地地址）
     */
    public List<String> pathToTextures;

    public CanvasData() {

    }

    public CanvasData(String canvasName, String localFeaturePath, String modelName, List<String> pathToTextures) {
        this.canvasName = canvasName;
        this.localFeaturePath = localFeaturePath;
        this.modelName = modelName;
        this.pathToTextures = pathToTextures;
    }

    @Override
    public String toString() {
        return "CanvasData{" +
                "canvasName='" + canvasName + '\'' +
                ", localFeaturePath='" + localFeaturePath + '\'' +
                ", modelName='" + modelName + '\'' +
                ", modelType='" + modelType + '\'' +
                ", position=" + Arrays.toString(position) +
                ", pathToTextures=" + pathToTextures +
                '}';
    }
}
