package com.utmaximur.utils.extensions

fun String.decimalToFloat(): Float {
    return this.replace(",", ".").toFloat()
}