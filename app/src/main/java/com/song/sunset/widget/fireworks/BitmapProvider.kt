package com.song.sunset.widget.fireworks

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.LruCache


import com.song.sunset.R

import java.util.ArrayList
import java.util.stream.StreamSupport

import androidx.annotation.DrawableRes

/**
 * @author songmingwen
 * @description 烟花特效
 * @since 2018/10/16
 */
class BitmapProvider {

    internal class Default(cacheSize: Int, private val bitmapArray: List<Bitmap>) : Provider {

        private val bitmapLruCache: LruCache<Int, Bitmap> = LruCache(cacheSize)

        init {
            for (i in bitmapArray.indices) {
                bitmapLruCache.put(i, bitmapArray[i])
            }
        }

        override fun getBitmapByIndex(index: Int): Bitmap {
            val position = (Math.random() * bitmapArray.size).toInt()
            var bitmap: Bitmap? = bitmapLruCache.get(position)
            if (bitmap == null) {
                bitmap = bitmapArray[position]
                bitmapLruCache.put(position, bitmap)
            }
            return bitmap
        }
    }

    class Builder(internal var context: Context) {
        private var cacheSize: Int = 0
        @DrawableRes
        private var drawableResArray: List<Int>? = null
        private var drawableArray: List<Drawable>? = null
        private var bitmapArray: MutableList<Bitmap>? = null

        fun setCacheSize(cacheSize: Int): Builder {
            this.cacheSize = cacheSize
            return this
        }

        fun setDrawableResArray(@DrawableRes drawableResArray: List<Int>): Builder {
            this.drawableResArray = drawableResArray
            return this
        }

        fun setDrawableArray(drawableArray: List<Drawable>): Builder {
            this.drawableArray = drawableArray
            return this
        }

        fun setBitmapArray(bitmapArray: MutableList<Bitmap>): Builder {
            this.bitmapArray = bitmapArray
            return this
        }

        fun build(): Provider {

            if (cacheSize == 0) {
                cacheSize = 64
            }

            convertBitmap()

            if (bitmapArray == null || bitmapArray!!.isEmpty()) {
                bitmapArray = ArrayList()
                bitmapArray!!.add(BitmapFactory.decodeResource(context.resources, R.drawable.fireworks_emoji001))
            }
            return Default(cacheSize, bitmapArray!!)
        }

        private fun convertBitmap() {
            if (bitmapArray != null && !bitmapArray!!.isEmpty()) {
                return
            }
            if (drawableResArray != null && !drawableResArray!!.isEmpty()) {
                bitmapArray = ArrayList()
                drawableResArray!!.map { res -> BitmapFactory.decodeResource(context.resources, res) }
                        .forEach { (bitmapArray as ArrayList<Bitmap>).add(it) }
                return
            }
            if (drawableArray != null && !drawableArray!!.isEmpty()) {
                bitmapArray = ArrayList()

                drawableArray!!.filter { it is BitmapDrawable }
                        .map { drawable -> (drawable as BitmapDrawable).bitmap }
                        .forEach { (bitmapArray as ArrayList<Bitmap>).add(it) }
            }
        }
    }


    interface Provider {

        fun getBitmapByIndex(index: Int): Bitmap
    }
}
