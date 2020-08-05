package com.song.sunset.utils.fresco;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CancellationException;

import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/20
 */
public class ImageIo {
    private static final String TAG = "ImageIO";

    private static abstract class FetchDataSubscriber extends BaseDataSubscriber<CloseableReference<PooledByteBuffer>> {
        private String mUrl;

        private FetchDataSubscriber(String url) {
            mUrl = url;
        }

        @Override
        protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
            CloseableReference<PooledByteBuffer> buffer = dataSource.getResult();
            if (buffer == null) {
                onFailureImpl(null);
                return;
            }

            File file = ImageCacheUtils.insertToMainFileCache(mUrl, buffer);
            if (file == null) {
                onFailureImpl(null);
                return;
            }

            onFileCacheInserted(file);
        }

        abstract void onFileCacheInserted(File file);
    }

    private static class PrefetchDataSubscriber extends BaseDataSubscriber<Void> {
        private CompletableEmitter mEmitter;

        private PrefetchDataSubscriber(CompletableEmitter emitter) {
            mEmitter = emitter;
        }

        @Override
        protected void onNewResultImpl(DataSource<Void> dataSource) {
            mEmitter.onComplete();
        }

        @Override
        protected void onFailureImpl(DataSource<Void> dataSource) {
            mEmitter.tryOnError(getDataSourceException(dataSource, "Failure to prefetch to bitmap cache"));
        }

        @Override
        public void onProgressUpdate(DataSource<Void> dataSource) {
            if (mEmitter.isDisposed()) {
                onFailureImpl(dataSource);
            }
        }
    }

    /**
     * 表示 {@link #cacheImageFile}, {@link #cacheImageFileWithProgress}
     * 和 {@link #fetchBitmap} 的结果。
     * <p>
     * 使用 {@link #getProgress()} 方法来获得进度，范围为 [0,1]。
     * 如果 {@link #getProgress()} 是 1f，那么可以通过 {@link #getResult()} 方法来得到结果。
     */
    public static class Result<T> {
        private float mProgress;
        private T mResult;
        private String mOriginalUrl;

        private Result(float progress, T result, String originalUrl) {
            set(progress, result, originalUrl);
        }

        private Result(String OriginalUrl) {
            this(0f, null, OriginalUrl);
        }

        private void set(float progress, T result, String originalUrl) {
            mProgress = progress;
            mResult = result;
            mOriginalUrl = originalUrl;
        }

        public float getProgress() {
            return mProgress;
        }

        public T getResult() {
            return mResult;
        }

        public String getOriginalUrl() {
            return mOriginalUrl;
        }
    }

    /**
     * 获取缓存的文件，如果该图片还没被缓存，那么将返回 null。
     * 注意此为同步方法，建议使用 {@link #getCachedFile(String)}。
     *
     * @return 如果没有缓存则返回 null
     */
    public static File getCachedFileSync(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            ImagePipelineFactory factory = ImagePipelineFactory.getInstance();
            CacheKey key = DefaultCacheKeyFactory.getInstance()
                    .getEncodedCacheKey(ImageRequest.fromUri(url), null);

            BinaryResource resource = null;
            if (factory.getMainFileCache().hasKey(key)) {
                resource = factory.getMainFileCache().getResource(key);
            } else if (factory.getSmallImageFileCache().hasKey(key)) {
                resource = factory.getSmallImageFileCache().getResource(key);
            }

            if (resource != null && resource instanceof FileBinaryResource) {
                File file = ((FileBinaryResource) resource).getFile();
                if (file != null && file.exists()) {
                    return file;
                }
            }

            return null;
        }
    }

    /**
     * 获取缓存的文件，返回 Maybe 流以订阅。如果图片已经被缓存了，那么 onSuccess 方法将被调用。
     */
    public static Maybe<File> getCachedFile(String url) {
        return Maybe.create(new MaybeOnSubscribe<File>() {
            @Override
            public void subscribe(MaybeEmitter<File> e) throws Exception {
                if (TextUtils.isEmpty(url)) {
                    e.onError(new IllegalArgumentException("Url should NOT be null or empty"));
                    return;
                }

                File file = getCachedFileSync(url);
                if (file != null) {
                    e.onSuccess(file);
                } else {
                    e.onComplete();
                }
            }
        });
    }

    /**
     * 同步方法，给定一个 url，判断是否存在对应的文件缓存。
     */
    public static boolean isInDiskCacheSync(String url) {
        return Fresco.getImagePipeline().isInDiskCacheSync(Uri.parse(url));
    }

    /**
     * 检查图片是否已经在磁盘缓存里了，如果需要同步调用，那么使用 {@link #isInDiskCacheSync(String)}。
     */
    public static Single<Boolean> isInDiskCache(String url) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> e) throws Exception {
                if (TextUtils.isEmpty(url)) {
                    e.onError(new IllegalArgumentException("Url should NOT be null or empty"));
                    return;
                }

                DataSource<Boolean> ds = Fresco.getImagePipeline().isInDiskCache(Uri.parse(url));
                ds.subscribe(new DataSubscriber<Boolean>() {
                    @Override
                    public void onNewResult(DataSource<Boolean> dataSource) {
                        e.onSuccess(dataSource.getResult() == null ? false : dataSource.getResult());
                    }

                    @Override
                    public void onFailure(DataSource dataSource) {
                        e.tryOnError(getDataSourceException(dataSource, "Failed to check is in disk cache"));
                    }

                    @Override
                    public void onCancellation(DataSource dataSource) {
                        // do nothing
                    }

                    @Override
                    public void onProgressUpdate(DataSource dataSource) {
                        // do nothing
                    }
                }, CallerThreadExecutor.getInstance());
            }
        });
    }

    /**
     * 检查图片是否在内存缓存里了。
     */
    public static boolean isInMemoryCache(String url) {
        if (TextUtils.isEmpty(url)) return false;
        return Fresco.getImagePipeline().isInBitmapMemoryCache(Uri.parse(url));
    }

    /**
     * 缓存并得到 Bitmap，订阅 Single 流来获得 Bitmap。
     * 如果需要进度，请使用 {@link #fetchBitmapWithProgress}。
     */
    public static Single<Result<Bitmap>> fetchBitmap(String url) {
        return fetchBitmapWithProgress(url).lastOrError();
    }

    /**
     * 参见 {@link #fetchBitmap}。
     */
    public static Observable<Result<Bitmap>> fetchBitmapWithProgress(String url) {
        return Observable.create(new ObservableOnSubscribe<Result<Bitmap>>() {
            private Result<Bitmap> mResult = new Result<>(url);
            private float mPreviousProgress = -1f;

            @Override
            public void subscribe(ObservableEmitter<Result<Bitmap>> e) throws Exception {
                if (TextUtils.isEmpty(url)) {
                    e.onError(new IllegalArgumentException("Url should NOT be null or empty"));
                    return;
                }

                DataSource<CloseableReference<CloseableImage>> ds = Fresco.getImagePipeline()
                        .fetchDecodedImage(ImageRequest.fromUri(url), this);
                ds.subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(@Nullable Bitmap bitmap) {
                        if (bitmap == null) {
                            onFailureImpl(ds);
                        } else {
                            Result<Bitmap> result = new Result<>(1f, bitmap, url);
                            e.onNext(result);
                            e.onComplete();
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                        e.tryOnError(getDataSourceException(dataSource, "Failed to fetch bitmap"));
                    }

                    @Override
                    public void onProgressUpdate(DataSource<CloseableReference<CloseableImage>> dataSource) {
                        if (e.isDisposed()) {
                            dataSource.close();
                        } else {
                            float currentProgress = dataSource.getProgress();
                            if (currentProgress - mPreviousProgress > 0.1) {
                                mPreviousProgress = currentProgress;
                                mResult.set(dataSource.getProgress(), null, url);
                                e.onNext(mResult);
                            }
                        }
                    }

                    @Override
                    public void onCancellation(DataSource<CloseableReference<CloseableImage>> dataSource) {
                        e.tryOnError(new IOException("Cancelled"));
                    }
                }, CallerThreadExecutor.getInstance());

            }
        });
    }

    /**
     * 预缓存图片到内存缓存，返回一个 Completable 流。
     * 如果需要拿到 Bitmap，那么请使用 {@link #fetchBitmap(String)}。
     */
    public static Completable prefetchToBitmapCache(String url) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                if (TextUtils.isEmpty(url)) {
                    e.onError(new IllegalArgumentException("Url should NOT be null or empty"));
                    return;
                }

                DataSource<Void> ds = Fresco.getImagePipeline().prefetchToBitmapCache(ImageRequest.fromUri(url), this);
                ds.subscribe(new PrefetchDataSubscriber(e), CallerThreadExecutor.getInstance());

            }
        });
    }

    /**
     * 预缓存图片到磁盘缓存。
     * 如果需要拿到文件，那么请使用 {@link #cacheImageFile(String)} 或者
     * {@link #cacheImageFileWithProgress(String)}。
     * <p>
     * 注意，当 {@link Completable} 完成的时候，调用 {@link #isInDiskCacheSync(String)} 和
     * {@link #isInDiskCache(String)} 等方法可能不会马上拿到文件。
     */
    public static Completable prefetchToDiskCache(String url) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                if (TextUtils.isEmpty(url)) {
                    e.onError(new IllegalArgumentException("Url should NOT be null or empty"));
                    return;
                }

                DataSource<Void> ds = Fresco.getImagePipeline().prefetchToDiskCache(ImageRequest.fromUri(url), this);
                ds.subscribe(new PrefetchDataSubscriber(e), CallerThreadExecutor.getInstance());

            }
        });
    }

    /**
     * 参见 {@link #fetchEncodedImage}。
     */
    public static Single<Result<InputStream>> fetchEncodedImage(String url) {
        return fetchEncodedImageWithProgress(url).lastOrError();
    }

    /**
     * 获取未经解码的图片流。
     * 注意，需要调用方手动关闭此 {@link InputStream} 流。
     */
    public static Observable<Result<InputStream>> fetchEncodedImageWithProgress(String url) {
        return Observable.create(new ObservableOnSubscribe<Result<InputStream>>() {
            private Result<InputStream> mResult = new Result<>(url);

            private float mPreviousProgress = -1f;

            @Override
            public void subscribe(ObservableEmitter<Result<InputStream>> e) throws Exception {
                if (TextUtils.isEmpty(url)) {
                    e.onError(new IllegalArgumentException("Url should NOT be null or empty"));
                    return;
                }

                DataSource<CloseableReference<PooledByteBuffer>> ds = Fresco.getImagePipeline()
                        .fetchEncodedImage(ImageRequest.fromUri(url), this);
                ds.subscribe(new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
                    @Override
                    protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                        CloseableReference<PooledByteBuffer> ref = dataSource.getResult();
                        if (ref != null && ref.get() != null) {
                            Result<InputStream> result = new Result<>(1f, new ImageInputStream(ref, dataSource), url);
                            e.onNext(result);
                            e.onComplete();
                        } else {
                            onFailureImpl(dataSource);
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                        e.tryOnError(getDataSourceException(dataSource, "Failed to fetch encoded bitmap"));
                    }

                    @Override
                    public void onProgressUpdate(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                        if (e.isDisposed()) {
                            dataSource.close();
                        } else {
                            float currentProgress = dataSource.getProgress();
                            if (currentProgress - mPreviousProgress > 0.1) {
                                mPreviousProgress = currentProgress;
                                mResult.set(dataSource.getProgress(), null, url);
                                e.onNext(mResult);
                            }
                        }
                    }

                    @Override
                    public void onCancellation(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                        e.tryOnError(new CancellationException("Cancelled"));
                    }
                }, CallerThreadExecutor.getInstance());

            }
        });
    }

    /**
     * 缓存图片到磁盘，并获得文件路径。
     * 使用 {@link #cacheImageFileWithProgress} 方法获取进度。
     */
    public static Single<Result<String>> cacheImageFile(String url) {
        return cacheImageFileWithProgress(url).lastOrError();
    }

    /**
     * 参见 {@link #cacheImageFile}。
     */
    public static Observable<Result<String>> cacheImageFileWithProgress(String url) {
        return Observable.create(new ObservableOnSubscribe<Result<String>>() {
            private Result<String> mResult = new Result<>(url);

            private float mPreviousProgress = -1f;

            @Override
            public void subscribe(ObservableEmitter<Result<String>> e) throws Exception {
                if (TextUtils.isEmpty(url)) {
                    e.onError(new IllegalArgumentException("Url should NOT be null or empty"));
                    return;
                }

                DataSource<CloseableReference<PooledByteBuffer>> ds = Fresco.getImagePipeline()
                        .fetchEncodedImage(ImageRequest.fromUri(url), this);
                ds.subscribe(new FetchDataSubscriber(url) {
                    @Override
                    void onFileCacheInserted(File file) {
                        Result<String> result = new Result<>(1f, file.getPath(), url);
                        result.set(1f, file.getPath(), url);
                        e.onNext(result);
                        e.onComplete();
                    }

                    @Override
                    public void onProgressUpdate(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                        if (e.isDisposed()) {
                            ds.close();
                        } else {
                            float currentProgress = dataSource.getProgress();
                            if (currentProgress - mPreviousProgress > 0.1) {
                                mPreviousProgress = currentProgress;
                                mResult.set(dataSource.getProgress(), null, url);
                                e.onNext(mResult);
                            }
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                        e.tryOnError(new IOException("Failed to cache image"));
                    }

                    @Override
                    public void onCancellation(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                        e.tryOnError(new CancellationException("Cancelled"));
                    }
                }, CallerThreadExecutor.getInstance());

            }
        });
    }

    /**
     * 清理所有缓存，包括内存和磁盘的。
     */
    public static void clearCaches() {
        Fresco.getImagePipeline().clearCaches();
    }

    /**
     * 清理内存缓存。
     */
    public static void clearMemoryCaches() {
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    /**
     * 清理磁盘缓存。
     */
    public static void clearDiskCaches() {
        Fresco.getImagePipeline().clearDiskCaches();
    }

    /**
     * 获取磁盘缓存的大小，单位为字节。
     */
    public static long getCacheSizeInBytes() {
        Fresco.getImagePipelineFactory().getMainFileCache().trimToMinimum();
        return Fresco.getImagePipelineFactory().getMainFileCache().getSize();
    }

    private static Throwable getDataSourceException(DataSource ds, String message) {
        Throwable t = ds.getFailureCause();
        if (t == null) {
            t = new IOException(message);
        }
        return t;
    }
}
