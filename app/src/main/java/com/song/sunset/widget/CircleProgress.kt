package com.song.sunset.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.song.sunset.R
import com.song.sunset.utils.ViewUtil

/**
 * @author songmingwen
 * @description 自定义带有进度的圆环；
 * 可以自定义内外层圆环的颜色、stroke 宽度，外层圆环运动速度
 * 设置当前外层圆环渲染的角度
 * @since 2019/8/15
 */
class CircleProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var mCircleStrokeWidth: Float = 0f
    private var mBgCircleStrokeWidth: Float = 0f
    private var mTargetRate: Int = 0
    private var mCurrentRate: Int = 0
    private var mSpeed: Int = 1

    private val mCirclePaint: Paint
    private val mBgCirclePaint: Paint

    private var mCircleRadius = 0f
    private var mCenterX = 0f
    private var mCenterY = 0f

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, defStyleRes)
        val circleColor = a.getColor(R.styleable.CircleProgress_circle_color, context.resources.getColor(R.color.colorPrimary))
        mCircleStrokeWidth = a.getFloat(R.styleable.CircleProgress_circle_stroke_width, 10F)
        mCircleStrokeWidth = ViewUtil.dip2px(mCircleStrokeWidth).toFloat()
        val bgCircleColor = a.getColor(R.styleable.CircleProgress_bg_circle_color, getContext().resources.getColor(R.color.Grey_400))
        mBgCircleStrokeWidth = a.getFloat(R.styleable.CircleProgress_bg_circle_stroke_width, 10f)
        mBgCircleStrokeWidth = ViewUtil.dip2px(mBgCircleStrokeWidth).toFloat()
        mCurrentRate = a.getInt(R.styleable.CircleProgress_current_angle_rate, 0)
        mSpeed = a.getInt(R.styleable.CircleProgress_circle_speed, 1)
        mTargetRate = mCurrentRate
        a.recycle()

        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = circleColor
            strokeWidth = mCircleStrokeWidth
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
        }

        mBgCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = bgCircleColor
            strokeWidth = mBgCircleStrokeWidth
            style = Paint.Style.STROKE
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val realWidth = w - paddingLeft - paddingRight
        val realHeight = h - paddingTop - paddingBottom

        mCenterX = (paddingLeft + w - paddingRight) / 2f
        mCenterY = (paddingTop + h - paddingBottom) / 2f

        val diameter = if (realWidth > realHeight) realHeight else realWidth
        val maxCircleStrokeWidth = if (mCircleStrokeWidth > mBgCircleStrokeWidth) mCircleStrokeWidth else mBgCircleStrokeWidth
        mCircleRadius = (diameter - maxCircleStrokeWidth) / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(mCenterX, mCenterY, mCircleRadius, mBgCirclePaint)

        canvas?.drawArc(
                mCenterX - mCircleRadius,
                mCenterY - mCircleRadius,
                mCenterX + mCircleRadius,
                mCenterY + mCircleRadius,
                -90f, (mCurrentRate.toFloat()), false, mCirclePaint)

        if (mTargetRate == mCurrentRate) {
            return
        }

        if (mTargetRate > mCurrentRate) {
            mCurrentRate += mSpeed
        } else {
            mCurrentRate -= mSpeed
        }

        mCurrentRate = when {
            mCurrentRate > 360 -> 360
            mCurrentRate < 0 -> 0
            else -> mCurrentRate
        }

        invalidate()
    }

    fun setAngleRate(rate: Int) {
        mTargetRate = when {
            rate > 360 -> 360
            rate < 0 -> 0
            else -> rate
        }
        if (mTargetRate == mCurrentRate) {
            return
        }
        invalidate()
    }

    fun getCurrnetAngle(): Int {
        return mCurrentRate
    }
}