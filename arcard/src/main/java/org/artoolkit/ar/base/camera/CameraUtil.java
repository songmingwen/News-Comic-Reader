package org.artoolkit.ar.base.camera;

import android.hardware.Camera;

import org.artoolkit.ar.base.log.ArLog;

import java.util.List;

/**
 * Desc:    相机相关工具类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/19 15:18
 */
public class CameraUtil {

    private static final String TAG = "CameraUtil";

    /**
     * 获取最佳预览大小
     * @param sizes 相机参数
     * @param containerWidth    显示区域宽
     * @param containerHeight   显示区域高
     */
    public static Camera.Size getSuitableSize(List<Camera.Size> sizes, int containerWidth, int containerHeight) {
        float temp;
        float minDelta = 100f; // 最小的差值，初始值应该设置大点保证之后的计算中会被重置
        float w_h = containerWidth * 1.0f / containerHeight;
        Camera.Size best = null;
        for (Camera.Size size : sizes) {
            if (size.height <= 480) {//剔除过小的分辨率尺寸
                continue;
            }
            temp = Math.abs((size.height * 1.0f / size.width) - w_h);
            if (temp < minDelta) {
                minDelta = temp;
                best = size;
            }
        }
        if (best != null) {
            ArLog.i(TAG, "origin w_h=" + containerWidth + "_" + containerHeight +
                    "; best w_h = " + best.width + "_" + best.height);
        }
        return best; // 默认返回与设置的分辨率最接近的预览尺寸
    }
}
