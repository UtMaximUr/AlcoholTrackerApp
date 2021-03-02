package com.utmaximur.alcoholtracker.util

import android.content.Context
import android.util.DisplayMetrics


object Convert {

    fun dpToPx(dp: Int, context: Context): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}