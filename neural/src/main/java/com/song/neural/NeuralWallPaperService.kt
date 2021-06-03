package com.song.neural

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.text.TextUtils
import android.view.SurfaceHolder
import com.song.neural.NeuralNetWorksActivity.Companion.SP_NEURAL_NET_WORKS
import com.song.neural.NeuralNetWorksActivity.Companion.SP_NEURAL_NET_WORKS_PREVIEW

import com.song.sunset.utils.JsonUtil
import com.tencent.mmkv.MMKV

import java.util.ArrayList

/**
 * @author songmingwen
 * @description
 * @since 2019/4/4
 */
class NeuralWallPaperService : WallpaperService() {

    private val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

    private var mParams: NeuralParams? = null

    private var neuralNetWorksModel: NeuralNetWorksModel? = null

    override fun onCreateEngine(): WallpaperService.Engine {
//        return GLWallPagerEngine(this)
        return NeuralWallPaperEngine()
    }

    internal inner class NeuralWallPaperEngine : WallpaperService.Engine() {

        private var mVisible: Boolean = false

        private var mDotPaint: Paint? = null

        private var mLinePaint: Paint? = null

        private var mWidth: Int = 0

        private var mHeight: Int = 0

        private var mDots: ArrayList<Dot>? = null

        private var mHandler = Handler()

        private val drawTarget = Runnable { this.drawFrame() }

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)

            neuralNetWorksModel = NeuralNetWorksModel()

            mDotPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
            mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
            mDotPaint!!.color = resources.getColor(R.color.white)
            mLinePaint!!.color = resources.getColor(R.color.white)

            //  设置壁纸的触碰事件为true
            setTouchEventsEnabled(false)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            val rect = holder.surfaceFrame
            mWidth = rect.right
            mHeight = rect.bottom

        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            mVisible = visible
            if (visible) {
                initData()
                mHandler.post(drawTarget)
            } else {
                mHandler.removeCallbacks(drawTarget)
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            mHandler.removeCallbacksAndMessages(null)
        }

        private fun initData() {

            var string = mmkv.getString(SP_NEURAL_NET_WORKS_PREVIEW, "")
            if (TextUtils.isEmpty(string)) {
                string = mmkv.getString(SP_NEURAL_NET_WORKS, "")
            }

            mParams = JsonUtil.gsonToBean(
                    string,
                    NeuralParams::class.java)
            if (mParams != null) {
                mDots = neuralNetWorksModel!!.createDotsList(mParams!!.elementAmount,
                        mWidth.toFloat(), mHeight.toFloat(), mParams!!.speed.toFloat(), mParams!!.radius)
            }
        }

        private fun drawFrame() {
            val holder = surfaceHolder
            val canvas = holder.lockCanvas()
            if (canvas != null) {
                canvas.drawColor(Color.BLACK)
                if (mDots == null || mDots!!.isEmpty()) {
                    mHandler.post(drawTarget)
                }

                drawLines(canvas)

                drawDots(canvas)

                holder.unlockCanvasAndPost(canvas)
            }

            if (mVisible) {
                next()
                mHandler.post(drawTarget)
            }
        }

        private fun drawLines(canvas: Canvas) {
            if (mDots == null) {
                return
            }
            for (line in neuralNetWorksModel!!.linesList) {
                mLinePaint!!.alpha = line.alpha
                canvas.drawLine(line.startX, line.startY, line.stopX, line.stopY, mLinePaint!!)
            }
        }

        private fun drawDots(canvas: Canvas) {
            if (mDots == null) {
                return
            }
            for (dot in mDots!!) {
                mDotPaint!!.alpha = dot.alpha
                canvas.drawCircle(dot.x, dot.y, dot.radius, mDotPaint!!)
            }
        }

        private operator fun next() {
            if (mDots == null) {
                return
            }
            neuralNetWorksModel!!.next(mParams!!.connectionThreshold.toFloat())
        }
    }

}
