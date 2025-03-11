package com.utmaximur.data

fun interface Mapper<FROM, TO> {
    fun transform(from: FROM): TO
}
