package com.song.sunset.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import com.song.sunset.base.R
import com.song.sunset.utils.ScreenUtils

/**
 * Desc:    内部控件尺寸小于容器
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/11/30 17:39
 */
class DragLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        /*** 一米等于 5000 dp */
        const val MI_TO_DP = 5000f
    }

    private lateinit var viewDragHelper: ViewDragHelper
    private var flingView: View? = null
    private var leftPosi = 0
    private var topPosi = 0

    //把阻尼系数换算成 pixel,左边是加速度，右边是换算系数
    private var dampRatioValue: Float

    private var isInner = true

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DragLayout, defStyleAttr, defStyleRes)
        val dampRatio = a.getFloat(R.styleable.DragLayout_dampRatio, 2.0f)
        isInner = a.getBoolean(R.styleable.DragLayout_isInner, true)
        dampRatioValue = dampRatio * ScreenUtils.dp2Px(context, MI_TO_DP)

        viewDragHelper = ViewDragHelper.create(this, 1.0f,
                object : ViewDragHelper.Callback() {
                    override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                        return true
                    }

                    override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                        return if (isInner) {
                            getRealValue(left, 0, leftPosi)
                        } else {
                            getRealValue(left, leftPosi, 0)
                        }
                    }

                    override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                        return if (isInner) {
                            getRealValue(top, 0, topPosi)
                        } else {
                            getRealValue(top, topPosi, 0)
                        }

                    }

                    override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                        if (releasedChild === flingView) {
                            Log.i("DragLayout_released", "xSpeed = $xvel, ySpeed = $yvel")
                            val currentX = flingView!!.x
                            val currentY = flingView!!.y
                            // 计算最终位置
                            var finalX = (currentX + moveDistance(xvel)).toInt()
                            var finalY = (currentY + moveDistance(yvel)).toInt()
                            // 校验最终位置
                            if (isInner) {
                                finalX = getRealValue(finalX, 0, leftPosi)
                                finalY = getRealValue(finalY, 0, topPosi)
                            } else {
                                finalX = getRealValue(finalX, leftPosi, 0)
                                finalY = getRealValue(finalY, topPosi, 0)
                            }
                            viewDragHelper.settleCapturedViewAt(finalX, finalY)
                            invalidate()
                        }
                    }
                })
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    private fun moveDistance(speed: Float): Float {
        //物理公式
        val absDistance = speed * speed / 2f / dampRatioValue
        return if (speed > 0) absDistance else (-1f * absDistance)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        leftPosi = width - paddingLeft - paddingRight - flingView!!.width
        topPosi = height - paddingTop - paddingBottom - flingView!!.height
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        flingView = getChildAt(0)
    }

    private fun getRealValue(current: Int, min: Int, max: Int): Int {
        return when {
            current < min -> min
            current > max -> max
            else -> current
        }
    }

}