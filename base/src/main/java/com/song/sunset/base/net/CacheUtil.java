package com.song.sunset.base.net;


import com.song.sunset.base.utils.FileUtils;
import com.song.sunset.base.utils.SdCardUtil;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
public class CacheUtil {

    public static final int KB = 1024;
    public static final int MB = 1024 * KB;

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * MB;

    private static File getCacheDir() {
        //设置缓存路径
//        final File baseDir = AppConfig.getApp().getCacheDir();
//        final File baseDir = Environment.getExternalStorageDirectory();
        final File baseDir = FileUtils.getFile(SdCardUtil.getAppNormalSDCardPath());
        final File cacheDir = new File(baseDir, "SunsetHttpCache");
        return cacheDir;
    }

    public static Cache getCache() {
        return new Cache(getCacheDir(), HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
