package com.song.sunset.utils.fresco;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/20
 */
public class ImageCacheUtils {
    /**
     * 把 PooledByteBuffer 保存到 Fresco 的文件缓存里。
     *
     * @param uri    请求的 uri，用于获得 CacheKey
     * @param buffer 一般从 {@link ImagePipeline#fetchEncodedImage(ImageRequest, Object)} 获得
     * @return 如果操作成功，返回该文件，否则为 null
     */
    @WorkerThread
    @Nullable
    public static File insertToMainFileCache(Uri uri, CloseableReference<PooledByteBuffer> buffer) {
        if (uri == null) {
            return null;
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(ImageRequest.fromUri(uri), null);
        return insertToMainFileCache(cacheKey, buffer);
    }

    /**
     * 把 PooledByteBuffer 保存到 Fresco 的文件缓存里。
     *
     * @param url    请求的 url，用于获得 CacheKey
     * @param buffer 一般从 {@link ImagePipeline#fetchEncodedImage(ImageRequest, Object)} 获得
     * @return 如果操作成功，返回该文件，否则为 null
     */
    @WorkerThread
    @Nullable
    public static File insertToMainFileCache(String url,
                                             CloseableReference<PooledByteBuffer> buffer) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return insertToMainFileCache(Uri.parse(url), buffer);
    }

    /**
     * 把 PooledByteBuffer 保存到 Fresco 的文件缓存里。
     *
     * @param buffer 一般从 {@link ImagePipeline#fetchEncodedImage(ImageRequest, Object)} 获得
     * @return 如果操作成功，返回该文件，否则为 null
     */
    @WorkerThread
    @Nullable
    public static File insertToMainFileCache(CacheKey cacheKey,
                                             CloseableReference<PooledByteBuffer> buffer) {
        EncodedImage encodedImage = new EncodedImage(buffer);
        InputStream is = encodedImage.getInputStream();
        try {
            BinaryResource res = Fresco.getImagePipelineFactory().getMainFileCache()
                    .insert(cacheKey, os -> copyStream(is, os));
            if (res instanceof FileBinaryResource) {
                return ((FileBinaryResource) res).getFile();
            }
        } catch (IOException e) {
            // ignore
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return null;
    }

    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
}
