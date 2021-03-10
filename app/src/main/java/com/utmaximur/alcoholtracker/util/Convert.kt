package com.utmaximur.alcoholtracker.util


import android.content.Context
import android.content.res.Resources
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.utmaximur.alcoholtracker.R
import kotlin.math.roundToInt


private val density by lazy { Resources.getSystem().displayMetrics.density }
private val densityDpi by lazy { Resources.getSystem().displayMetrics.densityDpi }

fun Int.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun Int.pxToDp(): Float {
    return this / (densityDpi / 160f)
}

fun Float.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun Float.pxToDp(): Float {
    return this / (densityDpi / 160f)
}

fun View.alphaView(context: Context) {
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.alpha)
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
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
//    val displayMetrics = DisplayMetrics()
//    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//    windowManager.defaultDisplay.getMetrics(displayMetrics)
//    return displayMetrics.widthPixels
}