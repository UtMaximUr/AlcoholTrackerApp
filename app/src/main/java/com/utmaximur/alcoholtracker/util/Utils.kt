package com.utmaximur.alcoholtracker.util


import android.app.Service
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
import android.view.inputmethod.InputMethodManager
import com.utmaximur.alcoholtracker.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


private val density by lazy { Resources.getSystem().displayMetrics.density }

fun Int.dpToPx(): Int {
    return (this * density).roundToInt()
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
}

fun String.getIdRaw(context: Context): Int? {
    return context.resources.getIdentifier(
        this,
        "raw",
        context.packageName
    )
}

fun Float.format1f(): String? {
    return String.format("%.1f", this)
}

fun Double.formatDegree1f(): String? {
    return String.format("%.1f", this).replace(",", ".")
}

fun Long.formatDate(context: Context): String? {
    val sdf = SimpleDateFormat(
        context.resources.getString(R.string.date_format_pattern),
        Locale.getDefault()
    )
    return String.format("%s", sdf.format(Date(this)))
}

fun String.formatVolume(context: Context, quantity: Int): String? {
    return ((
            this.replace(
                context.getString(R.string.only_number_regex).toRegex(),
                ""
            )
            ).toDouble() * quantity).toString() + this.replace(
        context.getString(R.string.only_text_regex).toRegex(),
        ""
    )
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}