package com.song.sunset.utils.fresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.song.sunset.R;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.retrofit.OkHttpClient;
import com.song.sunset.views.LoadingDisplayProgress;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class FrescoUtil {

    public static final int NO_PARAMS = -1;

    public static void setFrescoImage(SimpleDraweeView simpleDraweeView, String url) {
        simpleDraweeView.setHierarchy(getHierarchy(NO_PARAMS, false));
        simpleDraweeView.setImageURI(url);
    }

    public static void setFrescoCoverImage(SimpleDraweeView simpleDraweeView, String url, int width, int height) {
        setFrescoCoverImage(simpleDraweeView, url, width, height, true);
    }

    public static void setFrescoImageWith2Url(SimpleDraweeView simpleDraweeView, String lowResUrl, String originalUrl, int width, int height) {
        setFrescoImage(simpleDraweeView, lowResUrl, originalUrl, NO_PARAMS, width, height, false, false);
    }

    public static void setFrescoCoverImage(SimpleDraweeView simpleDraweeView, String url, int width, int height, boolean hasBorder) {
        setFrescoImage(simpleDraweeView, null, url, NO_PARAMS, width, height, hasBorder, false);
    }

    public static void setFrescoComicImage(SimpleDraweeView simpleDraweeView, String url, int position, int width, int height) {
        setFrescoImage(simpleDraweeView, null, url, position, width, height, false, false);
    }

    public static void setFrescoImage(SimpleDraweeView simpleDraweeView, String lowResUrl, String originalUrl, int position, int width, int height, boolean hasBorder, boolean userBlur) {
        simpleDraweeView.getLayoutParams().width = width;
        simpleDraweeView.getLayoutParams().height = height;
        simpleDraweeView.setHierarchy(getHierarchy(position, hasBorder));
        simpleDraweeView.setController(getController(simpleDraweeView, lowResUrl, originalUrl, width, height, userBlur));
    }

    /**
     * DraweeHierarchy 用于组织和维护最终绘制和呈现的 Drawable 对象，相当于MVC中的M。
     */
    public static GenericDraweeHierarchy getHierarchy(int position, boolean hasBorder) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(AppConfig.getApp().getResources());
        return builder
                .setPlaceholderImage(android.R.color.white)
                .setProgressBarImage(getProgressBarImage(position))
                .setRetryImage(R.drawable.icon_new_style_retry)
                .setRetryImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setFailureImage(R.drawable.icon_new_style_failure)
                .setRoundingParams(getRoundingParams(hasBorder))
                .setFadeDuration(50)
                .build();
    }

    public static Drawable getProgressBarImage(int position) {
        if (position == NO_PARAMS * 2) {
            return null;
        } else {
            return new LoadingDisplayProgress(position);
        }
    }

    public static RoundingParams getRoundingParams(boolean hasBorder) {
        RoundingParams roundingParams = new RoundingParams();
        if (hasBorder) {
            roundingParams.setBorder(Color.LTGRAY, 0.5f);
            return roundingParams;
        } else {
            return null;
        }
    }

    /**
     * DraweeController 负责和 image loader 交互（ Fresco 中默认为 image pipeline, 当然你也可以指定别的），
     * 可以创建一个这个类的实例，来实现对所要显示的图片做更多的控制。
     */

    public static DraweeController getController(SimpleDraweeView simpleDraweeView, String lowResUrl, String originalUrl, int width, int height, boolean userBlur) {
        return Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(getImageRequest(lowResUrl, width, height, userBlur))
                .setImageRequest(getImageRequest(originalUrl, width, height, userBlur))
                .setOldController(simpleDraweeView.getController())
                .setControllerListener(getControllerListener())
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .build();
    }

    public static ImageRequest getImageRequest(String url, int width, int height, boolean userBlur) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Uri uri = Uri.parse(url);
        return ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(false) //渐变加载
                .setLocalThumbnailPreviewsEnabled(true)
                .setPostprocessor(getPostProcessor(userBlur))
//                .setResizeOptions(getResizeOption(width, height))
//                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
//                .setImageDecodeOptions()
                .build();
    }

    public static ResizeOptions getResizeOption(int width, int height) {
        if (width == NO_PARAMS || height == NO_PARAMS) {
            return null;
        } else {
            return new ResizeOptions(width, height);
        }
    }

    public static ControllerListener<? super ImageInfo> getControllerListener() {
        ControllerListener listener = new BaseControllerListener() {
            @Override
            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                if (animatable != null) {
                    // 其他控制逻辑
                    animatable.start();
                }
            }

            @Override
            public void onSubmit(String id, Object callerContext) {
                super.onSubmit(id, callerContext);
//                Log.i("onSubmit", "---" + id + "---");
            }

            @Override
            public void onRelease(String id) {
                super.onRelease(id);
//                Log.i("onRelease", "---" + id + "---");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                Log.i("onFailure", "---" + id + "---" + throwable.toString());
            }
        };
        return listener;
    }

    public static Postprocessor getPostProcessor(boolean userBlur) {
        if (userBlur) {
            return new BasePostprocessor() {
                @Override
                public String getName() {
                    return "blurPostprocessor";
                }

                @Override
                public void process(Bitmap bitmap) {
                    BitmapBlurHelper.blur(bitmap, 35);
                }
            };
        } else {
            return null;
        }
    }

    /**
     * Fresco 的 Image Pipeline 负责图片的获取和管理。图片可以来自远程服务器，本地文件，
     * 或者Content Provider，本地资源。压缩后的文件缓存在本地存储中，Bitmap数据缓存在内存中。
     */

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

    public static ImagePipelineConfig getDefaultImagePipelineConfig(Context context) {

        //内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEMORY_CACHE_SIZE,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

        //修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        //小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(context.getApplicationContext().getCacheDir())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();

        //默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();

        //缓存图片配置
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
                .setResizeAndRotateEnabledForNetwork(true);

        // 就是这段代码，用于清理缓存
        NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();

                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                        ) {
                    ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
                }
            }
        });

        return configBuilder.build();
    }
}
