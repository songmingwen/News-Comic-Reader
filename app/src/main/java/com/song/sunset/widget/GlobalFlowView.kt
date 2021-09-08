package com.song.sunset.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.song.sunset.comic.utils.StatusBarUtil
import com.song.sunset.R
import com.song.sunset.utils.ViewUtil
import com.tencent.mmkv.MMKV

/**
 * @author songmingwen
 * @description
 * @since 2020/3/20
 */

class GlobalFlowView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    //获取系统能识别的最小滑动距离
    private val screenWidth = ViewUtil.getScreenWidth()
    private val screenHeight = ViewUtil.getScreenHeigth()
    private var layoutWidth: Int = -1
    private var layoutHeight: Int = -1

    private var limitTop = 0F
    private var limitBottom = 0F
    private var limitStart = 0F
    private var limitEnd = 0F

    init {
        View.inflate(context, R.layout.layout_global_flow_view, this)
        visibility = View.INVISIBLE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        layoutHeight = height
        layoutWidth = width
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                moveTo(event.rawX, event.rawY)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                dealWithAnim()
                return true
            }
            else -> return super.onTouchEvent(event)
        }
        return true
    }

    private fun dealWithAnim() {
        val store = MMKV.defaultMMKV()
        store.putFloat("show_global_flow_x", x)
        store.putFloat("show_global_flow_y", y)

        val destX: Float = getDestX(x)
        val destY: Float = getDestY(y)
        animate().run {
            cancel()
            translationX(destX)
                    .setDuration(200)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            if (context == null) {
                                return
                            }
                            animate().setListener(null)
                        }
                    })

            translationY(destY)
                    .setDuration(200)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            if (context == null) {
                                return
                            }
                            animate().setListener(null)
                        }
                    })
        }
    }

    private fun getDestY(y: Float): Float {
        val top = limitTop + StatusBarUtil.getStatusBarHeight(context)
        val bottom = screenHeight - limitBottom - layoutHeight
        return when {
            y < top -> top
            y > bottom -> bottom
            else -> y
        }
    }

    private fun getDestX(x: Float) =
            if (x > screenWidth / 2) screenWidth - layoutWidth - limitEnd else limitStart

    private fun moveTo(rawX: Float, rawY: Float) {
        val adjustX = rawX - (layoutWidth / 2)
        val adjustY = rawY - layoutHeight
        x = adjustX
        y = adjustY
    }

    fun initPosition() {
        val store = MMKV.defaultMMKV()
        val x = store.getFloat("show_global_flow_x", x)
        val y = store.getFloat("show_global_flow_y", y)
        translationX = getDestX(x)
        translationY = getDestY(y)
        visibility = View.VISIBLE
    }

}