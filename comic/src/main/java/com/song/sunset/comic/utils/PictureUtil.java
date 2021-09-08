package com.song.sunset.comic.utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.provider.MediaStore;

import java.io.FileNotFoundException;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/18 11:57
 */
public class PictureUtil {

    public static void saveImageToGallery(Context context, String filePath, String fileName) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    filePath, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String[] paths = new String[]{filePath};
        MediaScannerConnection.scanFile(context, paths, null, null);

    }
}
