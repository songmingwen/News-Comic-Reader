package com.bubblepicker.rendering

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.ColorInt
import com.bubblepicker.BubblePickerListener
import com.bubblepicker.adapter.BubblePickerAdapter
import com.bubblepicker.model.Color
import com.bubblepicker.model.PickerItem
import com.bubblepicker.physics.Engine.DEFAULT_RADIUS
import com.bubblepicker.physics.Engine.TOTAL_AMOUNT
import com.song.sunset.R

class BubblePicker : GLSurfaceView {

    /**
     * 背景色
     */
    @ColorInt
    var background: Int = 0
        set(value) {
            field = value
            renderer.backgroundColor = Color(value)
        }

    var adapter: BubblePickerAdapter? = null
        set(value) {
            if (value != null) {
                field = value
                renderer.items = ArrayList((0 until value.totalCount)
                        .map { value.getItem(it) }.toList())
            }
        }

    /**
     * 最多可以选中的数量
     */
    var maxSelectedCount: Int? = null
        set(value) {
            field = value
            renderer.maxSelectedCount = value
        }

    /**
     * 点击气泡监听：选中、取消选中
     */
    var listener: BubblePickerListener? = null
        set(value) {
            field = value
            renderer.listener = value
        }

    /**
     * 气泡大小，最小是 1，最大是 100；否则默认为默认值：TOTAL_AMOUNT
     */
    var bubbleSize = DEFAULT_RADIUS
        set(value) {
            if (value in 1..TOTAL_AMOUNT) {
                field = value
                renderer.bubbleSize = value
            }
        }

    /**
     * 获取选中的所有项目
     */
    val selectedItems: List<PickerItem?>
        get() = renderer.selectedItems

    /**
     * 是否立刻在屏幕中心显示
     */
    var centerImmediately = false
        set(value) {
            field = value
            renderer.centerImmediately = value
        }

    private val renderer = PickerRenderer(this)
    private var startX = 0f
    private var startY = 0f
    private var previousX = 0f
    private var previousY = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setZOrderOnTop(true)
        setEGLContextClientVersion(2)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(PixelFormat.RGBA_8888)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
        attrs?.let { retrieveAttributes(attrs) }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                previousX = event.x
                previousY = event.y
            }
            MotionEvent.ACTION_UP -> {
                if (isClick(event)) renderer.resize(event.x, event.y)
                renderer.release()
            }
            MotionEvent.ACTION_MOVE -> {
                if (isSwipe(event)) {
                    renderer.swipe(previousX - event.x, previousY - event.y)
                    previousX = event.x
                    previousY = event.y
                } else {
                    release()
                }
            }
            else -> release()
        }

        return true
    }

    private fun release() = postDelayed({ renderer.release() }, 0)

    private fun isClick(event: MotionEvent) = Math.abs(event.x - startX) < 20 && Math.abs(event.y - startY) < 20

    private fun isSwipe(event: MotionEvent) = Math.abs(event.x - previousX) > 20 && Math.abs(event.y - previousY) > 20

    private fun retrieveAttributes(attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.BubblePicker)

        if (array.hasValue(R.styleable.BubblePicker_maxSelectedCount)) {
            maxSelectedCount = array.getInt(R.styleable.BubblePicker_maxSelectedCount, -1)
        }

        if (array.hasValue(R.styleable.BubblePicker_backgroundColor)) {
            background = array.getColor(R.styleable.BubblePicker_backgroundColor, -1)
        }

        array.recycle()
    }

}
