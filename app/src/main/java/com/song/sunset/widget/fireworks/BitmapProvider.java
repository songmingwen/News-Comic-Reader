package com.song.sunset.widget.fireworks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.LruCache;

import com.song.sunset.R;

/**
 * @author songmingwen
 * @description 烟花特效
 * @since 2018/10/16
 */
public class BitmapProvider {

    static class Default implements Provider {

        private LruCache<Integer, Bitmap> bitmapLruCache;
        private @DrawableRes
        int[] drawableArray;
        private Context context;

        Default(Context context, int cacheSize, @DrawableRes int[] drawableArray) {
            bitmapLruCache = new LruCache<>(cacheSize);
            this.drawableArray = drawableArray;
            this.context = context;
        }

        @Override
        public Bitmap getBitmapByIndex(int index) {
            int position = (int) (Math.random() * drawableArray.length);
            Bitmap bitmap = bitmapLruCache.get(drawableArray[position]);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), drawableArray[position]);
                bitmapLruCache.put(drawableArray[position], bitmap);
            }
            return bitmap;
        }
    }

    public static class Builder {
        Context context;
        private int cacheSize;
        private @DrawableRes
        int[] drawableArray;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCacheSize(int cacheSize) {
            this.cacheSize = cacheSize;
            return this;
        }

        public Builder setDrawableArray(@DrawableRes int[] drawableArray) {
            this.drawableArray = drawableArray;
            return this;
        }

        public Provider build() {

            if (cacheSize == 0) {
                cacheSize = 32;
            }

            if (drawableArray == null || drawableArray.length == 0) {
                this.drawableArray = new int[]{R.drawable.fireworks_emoji001};
            }

            return new Default(context, cacheSize, drawableArray);
        }
    }


    public interface Provider {

        Bitmap getBitmapByIndex(int index);
    }
}
