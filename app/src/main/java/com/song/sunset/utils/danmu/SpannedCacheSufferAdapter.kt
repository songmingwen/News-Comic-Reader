package com.song.sunset.utils.danmu

import android.util.Log
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer

/**
 * <pre>
 *     Desc   : 弹幕样式适配器
 *     Author : songmingwen
 *     Time   : 2021/3/4 17:31
 * </pre>
 */
public class SpannedCacheSufferAdapter : BaseCacheStuffer.Proxy() {

    companion object {
        val TAG: String = SpannedCacheSufferAdapter::class.java.simpleName
    }

    override fun prepareDrawing(danmaku: BaseDanmaku?, fromWorkerThread: Boolean) {
        Log.i(TAG, "prepareDrawing" + danmaku?.text)
    }

    override fun releaseResource(danmaku: BaseDanmaku?) {
        Log.i(TAG, "releaseResource" + danmaku?.text)
    }
}