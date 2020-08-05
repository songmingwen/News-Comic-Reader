package com.song.sunset.utils.fresco;

import com.facebook.common.internal.Closeables;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/20
 */
class ImageInputStream extends InputStream {
    private CloseableReference<PooledByteBuffer> mRef;
    private DataSource<CloseableReference<PooledByteBuffer>> mDataSource;
    private PooledByteBufferInputStream mInputStream;

    public ImageInputStream(CloseableReference<PooledByteBuffer> ref,
                            DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
        mRef = ref;
        mDataSource = dataSource;
        mInputStream = new PooledByteBufferInputStream(ref.get());
    }

    @Override
    public int read() throws IOException {
        return mInputStream.read();
    }

    @Override
    public int available() throws IOException {
        return mInputStream.available();
    }

    @Override
    public synchronized void mark(int readLimit) {
        mInputStream.mark(readLimit);
    }

    @Override
    public boolean markSupported() {
        return mInputStream.markSupported();
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException {
        return mInputStream.read(b, off, len);
    }

    @Override
    public int read(@NonNull byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public synchronized void reset() throws IOException {
        mInputStream.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return mInputStream.skip(n);
    }

    @Override
    public void close() throws IOException {
        Closeables.closeQuietly(mInputStream);
        CloseableReference.closeSafely(mRef);

        if (mDataSource != null) {
            mDataSource.close();
        }
    }
}
