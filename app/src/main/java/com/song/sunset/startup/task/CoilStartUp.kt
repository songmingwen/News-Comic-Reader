package com.song.sunset.startup.task

import android.content.Context
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.util.CoilUtils
import okhttp3.OkHttpClient

class CoilStartUp private constructor() {

    companion object {
        val instance: CoilStartUp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CoilStartUp()
        }
    }

    fun init(context: Context): CoilStartUp {
        val imageLoader = ImageLoader.Builder(context)
                .crossfade(true)
                .componentRegistry {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder())
                    } else {
                        add(GifDecoder())
                    }
                    add(SvgDecoder(context))
                    add(VideoFrameFileFetcher(context))
                    add(VideoFrameUriFetcher(context))
                }
                .okHttpClient {
                    OkHttpClient.Builder()
                            .cache(CoilUtils.createDefaultCache(context))
                            .build()
                }
                .build()
        Coil.setImageLoader(imageLoader)
        return instance
    }
}
