package com.utmaximur.splash.ui.wave

import kotlin.math.sin
import kotlin.random.Random

data class Point(
    val index: Int,
    val x: Float,
    var y: Float,
    val fixedY: Float = y,
    var curSpeed: Float = index.toFloat(),
    val max: Double = Random(100).nextDouble() * 5 + 10
) {
    fun update(speed: Float) {
        curSpeed += speed
        y = fixedY + (sin(curSpeed) * max).toFloat()
    }
}