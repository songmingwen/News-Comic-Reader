/*
 * Copyright (c) 2016 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.song.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.song.sunset.base.net.HttpsUtil;

import okhttp3.OkHttpClient;

/**
 * Fresco 的 Image Pipeline 负责图片的获取和管理。图片可以来自远程服务器，本地文件，
 * 或者Content Provider，本地资源。压缩后的文件缓存在本地存储中，Bitmap数据缓存在内存中。
 */
public class FrescoInitializer implements ApplicationInitializer {

    //分配的可用内存
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    //使用的缓存数量
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

    //小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 20 * ByteConstants.MB;

    //小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 60 * ByteConstants.MB;

    //默认图极低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_VERYLOW_SIZE = 20 * ByteConstants.MB;

    //默认图低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_LOW_SIZE = 60 * ByteConstants.MB;

    //默认图磁盘缓存的最大值
    private static final int MAX_DISK_CACHE_SIZE = 100 * ByteConstants.MB;

    //小图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "Sunset/SunsetCacheSmall";

    //默认图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_CACHE_DIR = "Sunset/SunsetCacheDefault";

    public static FrescoInitializer getDefaultInstance() {
        return FrescoInitializer.InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {

        static final FrescoInitializer INSTANCE = new FrescoInitializer();
    }

    @Override
    public void initialize(final Context context) {
        //内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEMORY_CACHE_SIZE,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

        //修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = () -> bitmapCacheParams;

        //小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(context.getApplicationContext().getCacheDir())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();

        //默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(context.getApplicationContext().getExternalCacheDir())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();

        //缓存图片配置
        //自定义网络加载
        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(HttpsUtil.createDefaultSSLSocketFactory()).build();

        ImagePipelineConfig.Builder configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
                .setResizeAndRotateEnabledForNetwork(true);

        // 就是这段代码，用于清理缓存
        NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable(trimType -> {
            final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();

            if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                    || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                    || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                    ) {
                ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
            }
        });

        Fresco.initialize(context.getApplicationContext(), configBuilder.build());
    }
}