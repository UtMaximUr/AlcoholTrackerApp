package com.utmaximur.alcoholtracker.ui.customview


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorRes
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.util.dpToPx
import com.utmaximur.alcoholtracker.util.getDisplayWidth



class RangeSeekBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), View.OnTouchListener {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var onRightRangeChanged: ((currentRange: Float) -> Unit)? = null
    private var onLeftRangeChanged: ((currentRange: Float) -> Unit)? = null

    private var activeLineColor: Int = 0
    private var inActiveLineColor: Int = 0
    private var circleColor: Int = 0

    private var minRange = 0f
    private var maxRange = 100f
    private var seekbarStart = 20.dpToPx().toFloat()
    private var seekbarEnd = context.getDisplayWidth().toFloat() - 20.dpToPx()
    private var leftX: Float = seekbarStart
    private var rightX: Float = seekbarEnd
    private var floating = 0
    private val RIGHT = 1
    private val LEFT = 2

    init {
        paint.strokeWidth = 3.dpToPx().toFloat()
        initValues(attrs)
        setOnTouchListener(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        parent.requestDisallowInterceptTouchEvent(true)

        drawLines(canvas)
        drawCircles(canvas)
    }

    private fun initValues(attrs: AttributeSet?) {
        val typedValues = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar)
        activeLineColor = typedValues.getColor(R.styleable.RangeSeekBar_activeLineColor, context.resources.getColor(R.color.seekBarActiveColor))
        inActiveLineColor = typedValues.getColor(R.styleable.RangeSeekBar_inActiveLineColor, context.resources.getColor(R.color.seekBarInactiveColor))
        circleColor = typedValues.getColor(R.styleable.RangeSeekBar_circleColor, context.resources.getColor(R.color.seekBarActiveColor))

        maxRange = typedValues.getFloat(R.styleable.RangeSeekBar_maxRange, 100f)
        minRange = typedValues.getFloat(R.styleable.RangeSeekBar_minRange, 0f)

        rightX = seekbarEnd - (maxRange - typedValues.getFloat(R.styleable.RangeSeekBar_currentRangeMax, maxRange)) / (maxRange - minRange) * (seekbarEnd - seekbarStart)
        leftX = seekbarStart + (typedValues.getFloat(R.styleable.RangeSeekBar_currentRangeMin, minRange) - minRange) / (maxRange - minRange) * (seekbarEnd - seekbarStart)

        typedValues.recycle()
        circlePaint.color = circleColor

    }

    private fun drawCircles(canvas: Canvas?) {
        canvas?.drawCircle(
            leftX,
            20.dpToPx().toFloat(),
            8.dpToPx().toFloat(),
            circlePaint
        )

        //right circle
        canvas?.drawCircle(
            rightX,
            20.dpToPx().toFloat(),
            8.dpToPx().toFloat(),
            circlePaint
        )
    }

    private fun drawLines(canvas: Canvas?) {
        //first gray line
        paint.color = inActiveLineColor
        canvas?.drawLine(
            20.dpToPx().toFloat(),
            20.dpToPx().toFloat(),
            leftX,
            20.dpToPx().toFloat(),
            paint
        )

        //middle red line
        paint.color = activeLineColor
        canvas?.drawLine(
            leftX,
            20.dpToPx().toFloat(),
            rightX,
            20.dpToPx().toFloat(),
            paint
        )

        //second gray line
        paint.color = inActiveLineColor
        canvas?.drawLine(
            rightX,
            20.dpToPx().toFloat(),
            context.getDisplayWidth().toFloat() - 20.dpToPx(),
            20.dpToPx().toFloat(),
            paint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(context.getDisplayWidth(), 40.dpToPx())
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                onClickDown(event.x)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                onMove(event.x)
                invalidate()
            }
        }
        return true
    }

    private fun onClickDown(x: Float) {
        val deltaLeft = kotlin.math.abs(leftX - x)
        val deltaRight = kotlin.math.abs(x - rightX)
        if (deltaLeft < deltaRight) {
            floating = LEFT
            leftX = if (x < seekbarStart) seekbarStart else x
            onLeftRangeChanged?.invoke(minRange + (leftX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange))
        } else if (deltaLeft == deltaRight) {
            if (x < leftX) {
                floating = LEFT
                leftX = if (x < seekbarStart) seekbarStart else x
                onLeftRangeChanged?.invoke(minRange + (leftX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange))
            } else {
                floating = RIGHT
                rightX = if (x > seekbarEnd) seekbarEnd else x
                onRightRangeChanged?.invoke(minRange + (rightX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange))
            }
        } else {
            floating = RIGHT
            rightX = if (x > seekbarEnd) seekbarEnd else x
            onRightRangeChanged?.invoke(minRange + (rightX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange))
        }
    }

    private fun onMove(x: Float) {
        if (floating == LEFT) {
            leftX = when {
                x > rightX -> rightX
                x < seekbarStart -> seekbarStart
                else -> {
                    x
                }
            }
            onLeftRangeChanged?.invoke(minRange + (leftX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange))
        } else {
            rightX = when {
                x > seekbarEnd -> seekbarEnd
                x < leftX -> leftX
                else -> {
                    x
                }
            }
            onRightRangeChanged?.invoke(minRange + (rightX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange))
        }
    }

    fun setMaxRange(maxRange: Float) {
        this.maxRange = maxRange
    }

    fun setMinRange(minRange: Float) {
        this.minRange = minRange
    }

    fun getMax(): Float {
        return maxRange
    }

    fun getMin(): Float {
        return minRange
    }

    fun setCurrentRangeMax(currentMax: Float) {
        rightX =
            seekbarEnd - (maxRange - currentMax) / (maxRange - minRange) * (seekbarEnd - seekbarStart)
        invalidate()
    }

    fun setCurrentRangeMin(currentMin: Float) {
        leftX =
            seekbarStart + (currentMin - minRange) / (maxRange - minRange) * (seekbarEnd - seekbarStart)
        invalidate()
    }

    fun getCurrentRangeMin(): Float {
        return minRange + (leftX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange)
    }

    fun getCurrentRangeMax(): Float {
        return if (rightX == seekbarEnd) {
            maxRange
        } else minRange + (rightX - seekbarStart) / (seekbarEnd - seekbarStart) * (maxRange - minRange)
    }

    fun setActiveLineColor(@ColorRes color: Int) {
        activeLineColor = context.resources.getColor(color)
    }

    fun setInActiveLineColor(@ColorRes color: Int) {
        inActiveLineColor = context.resources.getColor(color)
    }

    fun setCircleColor(@ColorRes color: Int) {
        circleColor = context.resources.getColor(color)
    }

    fun getActiveLineColor() = activeLineColor
    fun getInActiveLineColor() = inActiveLineColor
    fun getCircleColor() = circleColor

    fun addMaxRangeChangeListener(maxChangeListener: (Float) -> Unit) {
        onRightRangeChanged = maxChangeListener
    }

    fun addMinRangeChangeListener(minChangeListener: (Float) -> Unit) {
        onLeftRangeChanged = minChangeListener
    }
}