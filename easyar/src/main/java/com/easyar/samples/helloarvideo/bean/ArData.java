package com.easyar.samples.helloarvideo.bean;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Desc:    ar 数据
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/6/30 9:50
 */
public class ArData {

    @IntDef({IMG_STORAGE_TYPE.APP, IMG_STORAGE_TYPE.ASSETS, IMG_STORAGE_TYPE.ABSOLUTE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IMG_STORAGE_TYPE {
        /*** app路径 Android: 程序 持久化数据目录 iOS: 程序沙盒目录 Windows: 可执行文件（exe）目录 Mac: 可执行文件目录（如果app是一个bundle，这个目录在bundle内部） */
        int APP = 0;
        /*** assets路径 Android: assets 目录（apk内部） iOS: 可执行文件目录 Windows: EasyAR.dll所在目录 Mac: libEasyAR.dylib所在目录 注意: 如果你在使用Unity3D，这个路径是不同的。在Unity3D中它将会指向StreamingAssets目录。 */
        int ASSETS = 1;
        /*** 绝对路径（json/图片路径或视频文件路径）或url（仅视频文件） */
        int ABSOLUTE = 2;
    }

    @IntDef({VIDEO_TYPE.DEFAULT, VIDEO_TYPE.TRANS, VIDEO_TYPE.NET})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VIDEO_TYPE {
        /*** 本地视频 */
        int DEFAULT = 0;
        /*** 本地透明视频 */
        int TRANS = 1;
        /*** 网络视频 */
        int NET = 2;
    }

    private final String imgPath;

    private final int imgStorageType;

    private final String imgName;

    private final String videoPath;

    private final int videoType;

    public ArData(String imgName, String picPath, @IMG_STORAGE_TYPE int imgStorageType, String videoPath,
                  @VIDEO_TYPE int videoType) {
        this.imgName = imgName;
        this.imgPath = picPath;
        this.imgStorageType = imgStorageType;
        this.videoPath = videoPath;
        this.videoType = videoType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getImgName() {
        return imgName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public int getVideoType() {
        return videoType;
    }

    public int getImgStorageType() {
        return imgStorageType;
    }
}
