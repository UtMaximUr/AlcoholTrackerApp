package com.utmaximur.alcoholtracker.util

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.setImage(icon: String) {
    Glide.with(this).load(
        icon.getIdRaw(context)
    ).into(this)
}

fun ImageView.setImage(icon: Int) {
    Glide.with(this).load(icon)
        .override(45.dpToPx(), 45.dpToPx()).into(
            this
        )
}

fun ImageView.setImagePath(path: String) {
    Glide.with(this).load(path).into(this)
}