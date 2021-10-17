package com.utmaximur.alcoholtracker.presentation.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs

class SwipeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var mViewDragHelper: ViewDragHelper
    private lateinit var mContentView: View
    private lateinit var mMenuView: View
    private var mMenuWidth: Int = 0
    private var mTouchSlop = 0
    private var menuIsOpen = false

    fun isOpen(): Boolean {
        return menuIsOpen
    }

    private var mScreenWidth: Int
    private var forceClose = false
    private var mGestureDetector: GestureDetector

    init {
        mViewDragHelper = ViewDragHelper.create(this, MyDragHelperCallback())

        mGestureDetector = GestureDetector(context, GestureDetectorCallback())
        mScreenWidth = context.resources.displayMetrics.widthPixels
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        mContentView = getChildAt(1)
        mMenuView = getChildAt(0)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mMenuWidth = mMenuView.measuredWidth
    }

    private var leftOffset = 0

    inner class MyDragHelperCallback : ViewDragHelper.Callback() {
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            leftOffset = left
            if (leftOffset < -mMenuWidth)
                leftOffset = -mMenuWidth
            else if (leftOffset >= 0)
                leftOffset = 0

            return leftOffset
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (forceClose) {
                menuIsOpen = false
                forceClose = false
                leftOffset = 0
                mViewDragHelper.settleCapturedViewAt(0, 0)
                invalidate()
                return
            }

            menuIsOpen = if (leftOffset >= -mMenuWidth / 2) {
                mViewDragHelper.settleCapturedViewAt(0, 0)
                false
            } else {
                mViewDragHelper.settleCapturedViewAt(-mMenuWidth, 0)
                true
            }
            invalidate()
        }

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child == mContentView
        }
    }

    private var downX = 0f
    private var downY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mViewDragHelper.processTouchEvent(ev)
                downX = ev.x
                downY = ev.y
                if (menuIsOpen) {
                    if (downX < mScreenWidth - mMenuWidth) {

                        forceClose = true
                        return true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = ev.y - downY
                val dx = ev.x - downX

                if (!menuIsOpen) {
                    return !(abs(dx) < mTouchSlop && abs(dy) < mTouchSlop)
                }

            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mGestureDetector.onTouchEvent(event)) return true
        mViewDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    fun closeMenu() {
        if (mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0)) {
            menuIsOpen = false
            forceClose = false
            leftOffset = 0
            invalidate()

        }
    }

    inner class GestureDetectorCallback : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return false
        }
    }
}