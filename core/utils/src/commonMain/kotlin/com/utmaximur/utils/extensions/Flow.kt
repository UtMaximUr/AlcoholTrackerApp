package com.utmaximur.utils.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, R> Flow<List<T>>.mapList(transform: (T) -> R): Flow<List<R>> {
    return this.map { list -> list.map(transform) }
}