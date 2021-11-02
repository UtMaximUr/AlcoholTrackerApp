package com.utmaximur.alcoholtracker.util

import android.app.Service
import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.util.extension.empty
import java.util.*

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
    this.clearFocus()
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.alphaView() {
    val animation: Animation = AnimationUtils.loadAnimation(this.context, R.anim.alpha_1000)
    this.startAnimation(animation)
}

fun View.alphaViewIn() {
    this.toVisible()
    val animation: Animation = AnimationUtils.loadAnimation(this.context, R.anim.alpha_in)
    this.startAnimation(animation)
}

fun View.alphaViewOut() {
    this.toInvisible()
    val animation: Animation = AnimationUtils.loadAnimation(this.context, R.anim.alpha_out)
    this.startAnimation(animation)
}

fun Context.getDisplayWidth(): Int {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

private fun NumberPicker.initNumberPicker() {
    this.children.iterator().forEach {
        if (it is EditText) it.width = LinearLayout.LayoutParams.WRAP_CONTENT
    }
}

fun NumberPicker.settingsNumberPicker() {
    this.maxValue = 10
    this.minValue = 1
}

fun NumberPicker.settingsNumberPicker(maxValue: Int, displayedValues: Array<String?>) {
    this.displayedValues = null
    this.maxValue = maxValue - 1
    this.displayedValues = displayedValues
    this.initNumberPicker()
}

fun NumberPicker.resetSettingsNumberPicker(maxValue: Int, displayedValues: Array<String?>) {
    this.displayedValues = null
    this.minValue = 0
    this.maxValue = maxValue
    this.displayedValues = displayedValues
}

fun ViewPager.addOnPageChangeListener(func: (Int) -> Unit, hide: () -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            hide()
        }

        override fun onPageSelected(position: Int) {
            func(position)
        }
    })
}

fun EditText.onEditorActionListener(hide: () -> Unit) {
    this.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hide()
            return@OnEditorActionListener true
        }
        true
    })
}

fun View.setupFullHeight() {
    val layoutParams = this.layoutParams
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    this.layoutParams = layoutParams
}

fun View.setTextOrEmpty(view: TextView, text: Any) {
    if (text == String.empty()) {
        this.toGone()
    } else {
        view.text = text.toString()
    }
}