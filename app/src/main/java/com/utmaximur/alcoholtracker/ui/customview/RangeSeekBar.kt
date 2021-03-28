package com.utmaximur.alcoholtracker.ui.customview


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
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
    private var seekBarStart = 20.dpToPx().toFloat()
    private var seekBarEnd = context.getDisplayWidth().toFloat() - 20.dpToPx()
    private var leftX: Float = seekBarStart
    private var rightX: Float = seekBarEnd
    private var floating = 0
    private val rightFloating = 1
    private val leftFloating = 2

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
        activeLineColor = typedValues.getColor(
            R.styleable.RangeSeekBar_activeLineColor,
            ContextCompat.getColor(context, R.color.seekBarActiveColor)
        )
        inActiveLineColor = typedValues.getColor(
            R.styleable.RangeSeekBar_inActiveLineColor,
            ContextCompat.getColor(context, R.color.seekBarInactiveColor)
        )
        circleColor = typedValues.getColor(
            R.styleable.RangeSeekBar_circleColor,
            ContextCompat.getColor(context, R.color.seekBarActiveColor)
        )

        maxRange = typedValues.getFloat(R.styleable.RangeSeekBar_maxRange, 100f)
        minRange = typedValues.getFloat(R.styleable.RangeSeekBar_minRange, 0f)

        rightX = seekBarEnd - (maxRange - typedValues.getFloat(
            R.styleable.RangeSeekBar_currentRangeMax,
            maxRange
        )) / (maxRange - minRange) * (seekBarEnd - seekBarStart)
        leftX = seekBarStart + (typedValues.getFloat(
            R.styleable.RangeSeekBar_currentRangeMin,
            minRange
        ) - minRange) / (maxRange - minRange) * (seekBarEnd - seekBarStart)

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
        paint.color = inActiveLineColor
        canvas?.drawLine(
            20.dpToPx().toFloat(),
            20.dpToPx().toFloat(),
            leftX,
            20.dpToPx().toFloat(),
            paint
        )

        paint.color = activeLineColor
        canvas?.drawLine(
            leftX,
            20.dpToPx().toFloat(),
            rightX,
            20.dpToPx().toFloat(),
            paint
        )

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
            floating = leftFloating
            leftX = if (x < seekBarStart) seekBarStart else x
            onLeftRangeChanged?.invoke(minRange + (leftX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange))
        } else if (deltaLeft == deltaRight) {
            if (x < leftX) {
                floating = leftFloating
                leftX = if (x < seekBarStart) seekBarStart else x
                onLeftRangeChanged?.invoke(minRange + (leftX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange))
            } else {
                floating = rightFloating
                rightX = if (x > seekBarEnd) seekBarEnd else x
                onRightRangeChanged?.invoke(minRange + (rightX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange))
            }
        } else {
            floating = rightFloating
            rightX = if (x > seekBarEnd) seekBarEnd else x
            onRightRangeChanged?.invoke(minRange + (rightX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange))
        }
    }

    private fun onMove(x: Float) {
        if (floating == leftFloating) {
            leftX = when {
                x > rightX -> rightX
                x < seekBarStart -> seekBarStart
                else -> {
                    x
                }
            }
            onLeftRangeChanged?.invoke(minRange + (leftX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange))
        } else {
            rightX = when {
                x > seekBarEnd -> seekBarEnd
                x < leftX -> leftX
                else -> {
                    x
                }
            }
            onRightRangeChanged?.invoke(minRange + (rightX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange))
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
            seekBarEnd - (maxRange - currentMax) / (maxRange - minRange) * (seekBarEnd - seekBarStart)
        invalidate()
    }

    fun setCurrentRangeMin(currentMin: Float) {
        leftX =
            seekBarStart + (currentMin - minRange) / (maxRange - minRange) * (seekBarEnd - seekBarStart)
        invalidate()
    }

    fun getCurrentRangeMin(): Float {
        return minRange + (leftX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange)
    }

    fun getCurrentRangeMax(): Float {
        return if (rightX == seekBarEnd) {
            maxRange
        } else minRange + (rightX - seekBarStart) / (seekBarEnd - seekBarStart) * (maxRange - minRange)
    }

    fun setActiveLineColor(@ColorRes color: Int) {
        activeLineColor = ContextCompat.getColor(context,color)
    }

    fun setInActiveLineColor(@ColorRes color: Int) {
        inActiveLineColor = ContextCompat.getColor(context,color)
    }

    fun setCircleColor(@ColorRes color: Int) {
        circleColor = ContextCompat.getColor(context,color)
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