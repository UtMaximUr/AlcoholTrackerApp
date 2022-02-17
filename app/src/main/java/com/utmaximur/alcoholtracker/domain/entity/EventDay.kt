package com.utmaximur.alcoholtracker.domain.entity


import androidx.annotation.DrawableRes

data class EventDay(val date: Long) {
    internal var imageDrawable: Int = 0

    constructor(date: Long, @DrawableRes drawableRes: Int) : this(date) {
        imageDrawable = drawableRes
    }
}
