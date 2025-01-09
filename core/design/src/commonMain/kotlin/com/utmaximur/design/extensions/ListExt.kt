package com.utmaximur.design.extensions


fun <T> List<T>.isFirst(value: T): Boolean {
    return this.firstOrNull() == value
}

fun <T> List<T>.isLast(value: T): Boolean {
    return this.lastOrNull() == value
}