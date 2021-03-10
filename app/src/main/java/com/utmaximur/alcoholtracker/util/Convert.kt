package com.utmaximur.alcoholtracker.util


import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
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

fun AlphaView(view: View, context: Context) {
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.alpha)
    view.startAnimation(animation)
}

fun Context.getDisplayWidth(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}