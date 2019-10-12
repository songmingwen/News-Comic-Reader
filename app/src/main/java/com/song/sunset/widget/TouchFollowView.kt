package com.song.sunset.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by Song on 2017/8/18 0018.
 * E-mail: z53520@qq.com
 */

class TouchFollowView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint? = null
    private var mOutPaint: Paint? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var startX: Float = 0.toFloat()
    private var startY: Float = 0.toFloat()
    private var endX: Float = 0.toFloat()
    private var endY: Float = 0.toFloat()

    init {
        init()
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mOutPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mPaint!!.color = 0x772bbad8
        mOutPaint!!.color = 0x77ffa200
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(-0x1000000)

        val radius = Math.min(mWidth, mHeight) / 4
        val inRadius = Math.min(mWidth, mHeight) / 4
        val outRadius = Math.min(mWidth, mHeight) / 12
        //        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaint);

        // 锁定画布
        canvas.save()

        val transX = endX - startX
        val transY = endY - startY

        val distance = Math.sqrt((transX * transX + transY * transY).toDouble()).toInt()

        var inX = transX
        var inY = transY
        var outX = transX
        var outY = transY

        if (distance >= inRadius) {
            inX = inRadius * transX / distance
            inY = inRadius * transY / distance
        }

        if (distance >= outRadius) {
            outX = outRadius * transX / distance
            outY = outRadius * transY / distance
        }

        //移动画布使原始的圆心位于(0,0)的位置
        canvas.translate((mWidth / 2).toFloat(), (mHeight / 2).toFloat())

        canvas.drawCircle(outX, outY, radius * 1.5f, mOutPaint!!)
        canvas.drawCircle(inX, inY, radius * 0.6f, mPaint!!)

        // 释放画布
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.rawX
                startY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                endX = event.rawX
                endY = event.rawY
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                endY = 0f
                endX = endY
                startY = endX
                startX = startY
                invalidate()
            }
        }
        return true
    }
}
