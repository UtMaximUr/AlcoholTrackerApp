package com.utmaximur.alcoholtracker.ui.customview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import android.view.ViewConfiguration
import androidx.annotation.Nullable
import androidx.core.view.isVisible

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs


class SwipeRecyclerView(context: Context?, @Nullable attrs: AttributeSet?, defStyle: Int) :
    RecyclerView(context!!, attrs, defStyle) {
    private var mTouchSlop = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    //    @Override
    //    public boolean dispatchTouchEvent(MotionEvent e) {
    //
    //        switch (e.getAction()) {
    //            case MotionEvent.ACTION_DOWN:
    //
    //
    //                break;
    //            case MotionEvent.ACTION_MOVE:
    //
    //                break;
    //            case MotionEvent.ACTION_UP:
    //                break;
    //        }
    //
    //        return super.dispatchTouchEvent(e);
    //    }
    private var downX = 0f
    private var downY = 0f
    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.x
                downY = e.y
                val position = pointToPosition(e.x.toInt(), e.y.toInt())
                val count = childCount
                if (count > position) {
                    val positionView: View = getChildAt(position)
                    if (positionView is SwipeLayout) {
                        if (positionView.isOpen()) { //不拦截
                            return super.onInterceptTouchEvent(e)
                        }
                    }
                }
                if (hasChildOpen()) {
                    closeMenu()
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = e.y - downY
                val dx = e.x - downX
                if (abs(dx) > abs(dy) && abs(dx) > mTouchSlop) { //横向滑动
                    return false
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    private var touchFrame: Rect? = null
    private fun pointToPosition(x: Int, y: Int): Int {
        var frame: Rect? = touchFrame
        if (frame == null) {
            touchFrame = Rect()
            frame = touchFrame
        }
        val count = childCount
        for (i in 0 until count) {
            val child: View = getChildAt(i)
            if (child.isVisible) {
                child.getHitRect(frame)
                if (frame?.contains(x, y)!!) {
                    return i
                }
            }
        }
        return -1
    }

    private fun hasChildOpen(): Boolean {
        val count = childCount
        for (i in 0 until count) {
            val view: View = getChildAt(i)
            if (view is SwipeLayout) {
                if (view.isOpen()) {
                    return true
                }
            }
        }
        return false
    }

    private fun closeMenu() {
        val count = childCount
        for (i in 0 until count) {
            val view: View = getChildAt(i)
            if (view is SwipeLayout) {
                if (view.isOpen()) {
                    view.closeMenu()
                }
            }
        }
    }

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }
}