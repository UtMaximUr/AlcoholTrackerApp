package com.utmaximur.data

fun interface Mapper<FROM, TO> {
    fun transform(from: FROM): TO
}

fun interface IndexedMapper<F, T> {
    fun map(index: Int, from: F): T
}