package com.song.sunset.utils.fresco;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.song.sunset.R;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.widget.LoadingDisplayProgress;

import java.io.File;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class FrescoUtil {

    public static final int NO_PARAMS = -1;
    public static final int NO_CIRCLE = -2;

    public static void setFrescoImage(SimpleDraweeView simpleDraweeView, String url) {
        simpleDraweeView.setHierarchy(getHierarchy(NO_CIRCLE, false));
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
        if (position == NO_CIRCLE) {
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
//                .setCacheChoice(ImageRequest.CacheChoice.DEFAULT)
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
                    BitmapBlurHelper.blur(bitmap, 20);
                }
            };
        } else {
            return null;
        }
    }


    /**
     * 拿到缓存的文件
     *
     * @return file or null
     */
    public static File getCachedImageOnDisk(String pUrl, Object pCaller) {
        File localFile = null;
        if (!TextUtils.isEmpty(pUrl)) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                    .getEncodedCacheKey(ImageRequest.fromUri(pUrl), pCaller);
            BinaryResource resource = null;
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
            } else if (ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey)) {
                resource = ImagePipelineFactory.getInstance().getSmallImageFileCache().getResource(cacheKey);
            }
            if (resource != null) {
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }

    /**
     * 通过图片 url 获取对应的 DataSource
     */
    public static DataSource<CloseableReference<CloseableImage>> getDataSource(String url) {
        ImageRequest imageRequest = ImageRequest.fromUri(url);

        if (imageRequest == null) {
            return null;
        }

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        return imagePipeline.fetchDecodedImage(imageRequest, url);

    }

    /**
     * 通过 DataSource 获取对应的 bitmap
     */
    public static void getCachedImageBitmap(@NonNull DataSource<CloseableReference<CloseableImage>> dataSource, @NonNull
            BaseBitmapDataSubscriber dataSubscriber) {
        dataSource.subscribe(dataSubscriber, CallerThreadExecutor.getInstance());
    }

}
