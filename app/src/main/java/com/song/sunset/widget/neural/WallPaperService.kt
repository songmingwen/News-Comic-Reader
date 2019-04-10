package com.song.sunset.widget.neural

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.view.MotionEvent
import android.view.SurfaceHolder

import com.song.sunset.R

import java.util.ArrayList

/**
 * @author songmingwen
 * @description
 * @since 2019/4/4
 */
class WallPaperService : WallpaperService() {

    companion object {

        const val NEURAL_PARAMS = "com_song_sunset_neural_params"
    }

    private var mParams: NeuralParams? = NeuralParams(128, 128, 4, 0F)

    override fun onCreateEngine(): WallpaperService.Engine {
//        mParams = intent?.getBundleExtra(NEURAL_PARAMS) as NeuralParams?

        return WallPaperEngine()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    internal inner class WallPaperEngine : WallpaperService.Engine() {
        private var mVisible: Boolean = false

        private val DEFAULT_DOT_COLOR = resources.getColor(R.color.Grey_500)

        private val DEFAULT_LINE_COLOR = resources.getColor(R.color.Grey_500)

        private var mDotPaint: Paint? = null

        private var mLinePaint: Paint? = null

        /**
         * 控件宽度
         */
        private var mWidth: Int = 0

        /**
         * 控件高度
         */
        private var mHeight: Int = 0

        private var mDots: ArrayList<Dot>? = null

        var mHandler = Handler()

        private val drawTarget = Runnable { this.drawFrame() }

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)

            mDotPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
            mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
            mDotPaint!!.color = DEFAULT_DOT_COLOR
            mLinePaint!!.color = DEFAULT_LINE_COLOR

            //  设置壁纸的触碰事件为true
            setTouchEventsEnabled(true)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            val rect = holder.surfaceFrame
            mWidth = rect.right
            mHeight = rect.bottom
            mDots = NeuralNetWorksModel.getInstance().getDotList(mParams!!.elementAmount, mWidth.toFloat(), mHeight.toFloat(), mParams!!.speed.toFloat(), mParams!!.radius)

            drawFrame()
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            drawFrame()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            mVisible = visible
            if (visible) {
                mHandler.post(drawTarget)
            } else {
                //  如果界面不可见，删除回调
                mHandler.removeCallbacks(drawTarget)
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            //  删除回调
            mHandler.removeCallbacks(drawTarget)
        }

        override fun onTouchEvent(event: MotionEvent) {
            super.onTouchEvent(event)
        }

        private fun drawFrame() {
            mHandler.removeCallbacks(drawTarget)

            val holder = surfaceHolder
            val canvas = holder.lockCanvas()
            if (canvas != null) {
                canvas.drawColor(Color.BLACK)
                if (mDots == null || mDots!!.isEmpty()) {
                    return
                }

                drawLines(canvas)

                drawDots(canvas)

                holder.unlockCanvasAndPost(canvas)
            }

            if (mVisible) {
                next()
                mHandler.postDelayed(drawTarget, 16.6.toLong())
            }
        }

        private fun drawLines(canvas: Canvas) {
            for (line in NeuralNetWorksModel.getInstance().linesList) {
                mLinePaint!!.alpha = line.alpha
                canvas.drawLine(line.startX, line.startY, line.stopX, line.stopY, mLinePaint!!)
            }
        }

        private fun drawDots(canvas: Canvas) {
            for (dot in mDots!!) {
                mDotPaint!!.alpha = dot.alpha
                canvas.drawCircle(dot.x, dot.y, dot.radius, mDotPaint!!)
            }
        }

        private operator fun next() {
            NeuralNetWorksModel.getInstance().next(mParams!!.connectionThreshold.toFloat())
        }
    }

}
