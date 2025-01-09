package com.utmaximur.design.extensions

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.ui.graphics.Paint

actual fun Paint.applyBlurNativePaint(blurRadius: Float) {
    this.asFrameworkPaint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        if (blurRadius > 0) {
            maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
        }
    }
}