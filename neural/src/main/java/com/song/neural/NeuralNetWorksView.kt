package com.song.neural

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @author songmingwen
 * @description
 * @since 2019/4/3
 */
class NeuralNetWorksView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    private val DEFAULT_DOT_COLOR = context.resources.getColor(R.color.Grey_500)

    private val DEFAULT_LINE_COLOR = context.resources.getColor(R.color.Grey_500)

    private var mDotPaint: Paint? = null

    private var mLinePaint: Paint? = null

    /**
     * 连线阀值，小于此值时点与点之间会有连线
     */
    private var mConnectionThreshold: Int = 0

    /**
     * 点的数量
     */
    private var mElementAmount: Int = 0

    /**
     * 点的基础速度
     */
    private var mSpeed: Int = 0

    /**
     * 圆点半径
     */
    private var mRadius: Float = 0.toFloat()

    /**
     * 点的颜色
     */
    private var mDotColor: Int = 0

    /**
     * 线的颜色
     */
    private var mLineColor: Int = 0

    /**
     * 控件宽度
     */
    private var mWidth: Int = 0

    /**
     * 控件高度
     */
    private var mHeight: Int = 0

    private var mNeuralNetWorksModel: NeuralNetWorksModel? = null

    private val instance: NeuralNetWorksModel
        get() {
            if (mNeuralNetWorksModel == null) {
                mNeuralNetWorksModel = NeuralNetWorksModel()
            }
            return mNeuralNetWorksModel as NeuralNetWorksModel
        }

    init {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.NeuralNetWorksView, defStyleAttr, defStyleRes)
        mElementAmount = a.getInteger(R.styleable.NeuralNetWorksView_neural_element_amount, DEFAULT_ELEMENT_AMOUNT)
        mConnectionThreshold = a.getInteger(R.styleable.NeuralNetWorksView_neural_connection_threshold, DEFAULT_CONNECTION_THRESHOLD)
        mSpeed = a.getInteger(R.styleable.NeuralNetWorksView_neural_speed, DEFAULT_SPEED)
        mRadius = a.getFloat(R.styleable.NeuralNetWorksView_neural_dot_radius, DEFAULT_DOT_RADIUS.toFloat())
        mDotColor = a.getColor(R.styleable.NeuralNetWorksView_neural_dot_color, DEFAULT_DOT_COLOR)
        mLineColor = a.getColor(R.styleable.NeuralNetWorksView_neural_line_color, DEFAULT_LINE_COLOR)
        a.recycle()

        mDotPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mDotPaint!!.color = mDotColor
        mLinePaint!!.color = mLineColor

    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        mWidth = w - paddingLeft - paddingRight
        mHeight = h - paddingTop - paddingBottom
        setDots()
    }

    private fun setDots() {
        instance.clear()
        instance.createDotsList(mElementAmount, mWidth.toFloat(), mHeight.toFloat(), mSpeed.toFloat(), mRadius)
    }

    override fun onDraw(canvas: Canvas) {
        if (instance.dotsList == null) {
            return
        }

        drawLines(canvas)

        drawDots(canvas)

        next()

        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
    }

    fun cancel() {
        instance.clear()
    }

    private fun drawLines(canvas: Canvas) {
        for (line in instance.linesList) {
            mLinePaint!!.alpha = line.alpha
            canvas.drawLine(line.startX + paddingLeft, line.startY + paddingTop,
                    line.stopX + paddingLeft, line.stopY + paddingTop, mLinePaint!!)
        }
    }

    private fun drawDots(canvas: Canvas) {
        for (dot in instance.dotsList) {
            mDotPaint!!.alpha = dot.alpha
            canvas.drawCircle(dot.x + paddingLeft, dot.y + paddingTop, dot.radius, mDotPaint!!)
        }
    }

    private operator fun next() {
        instance.next(mConnectionThreshold.toFloat())
    }

    fun setConnectionThreshold(connectionThreshold: Int) {
        mConnectionThreshold = connectionThreshold
    }

    fun setElementAmount(elementAmount: Int) {
        mElementAmount = elementAmount
    }

    fun setSpeed(speed: Int) {
        mSpeed = speed
    }

    fun setRadius(radius: Float) {
        mRadius = radius
    }

    fun invalidateView() {
        setDots()
    }

    companion object {

        private const val DEFAULT_ELEMENT_AMOUNT = 64

        private const val DEFAULT_CONNECTION_THRESHOLD = 512

        private const val DEFAULT_SPEED = 4

        private const val DEFAULT_DOT_RADIUS = 4
    }
}
