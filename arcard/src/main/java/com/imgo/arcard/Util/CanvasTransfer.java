package com.imgo.arcard.Util;

import android.content.Context;

import com.imgo.arcard.Config.Canvas;
import com.imgo.arcard.Config.Model;
import com.imgo.arcard.Drawable.RectMovie;
import com.imgo.arcard.bean.CanvasData;
import com.imgo.arcard.bean.CanvasListData;

import java.util.ArrayList;
import java.util.List;

import static com.imgo.arcard.bean.CanvasData.DEFAULT_VERTEX_BUFFER;
import static com.imgo.arcard.bean.CanvasData.MODEL_TYPE_VIDEO;

/**
 * Desc:    模型、画布转化器
 * 用于将 json 特征文件、视频等数据转化为可以用来播放的 canvas 对象。
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/12 10:32
 */
public class CanvasTransfer {

    /**
     * 通过 bean 数据创建 canvas 列表
     */
    public static List<Canvas> createCanvasList(Context context, CanvasListData canvasListData) {
        if (canvasListData == null || canvasListData.canvasList == null || canvasListData.canvasList.isEmpty()) {
            return null;
        }
        List<Canvas> list = new ArrayList<>();
        for (CanvasData data : canvasListData.canvasList) {
            Canvas canvas = createCanvas(context, data.canvasName, data.localFeaturePath, data.modelName, data.pathToTextures);
            if (canvas != null) {
                list.add(canvas);
            }
        }
        return list;
    }

    public static Canvas createCanvas(Context context, String canvasName, String localFeaturePath,
                                      String modelName, List<String> pathToTextures) {
        return createCanvas(context, canvasName, localFeaturePath, modelName,
                MODEL_TYPE_VIDEO, DEFAULT_VERTEX_BUFFER, pathToTextures);
    }

    /**
     * @param canvasName       画布名称、或画布ID，唯一标识
     * @param localFeaturePath 本地特征文件路径
     * @param modelName        模型名称（如视频名称）
     * @param modelType        模型类型（如视频类型）
     * @param position         模型位置：三维位置数组，即矩形的四点
     * @param pathToTextures   包含纹理路径的列表（如视频本地地址）
     */
    public static Canvas createCanvas(Context context, String canvasName, String localFeaturePath,
                                      String modelName, String modelType, float[][] position,
                                      List<String> pathToTextures) {

        Canvas canvas = new Canvas(canvasName, localFeaturePath);//把特征文件路径关联到 canvas 结构中
        Model model;
        if (modelType.equalsIgnoreCase(MODEL_TYPE_VIDEO)) {
            model = new Model(modelName, new RectMovie(position, pathToTextures, context));
            canvas.addModelMovie(model);//把视频 model 关联到 canvas 结构中
        } else {
            return null;
        }
        return canvas;
    }
}
