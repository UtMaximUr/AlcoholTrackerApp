package com.utmaximur.utils.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Request<T> {
    class Loading<T> : Request<T>()
    data class Success<T>(val data: T) : Request<T>()
    data class Error<T>(val error: Throwable) : Request<T>()
}

fun <T> Flow<T>.asRequest(): Flow<Request<T>> = this
    .map { Request.Success(it) as Request<T> }
    .onStart { emit(Request.Loading()) }
    .catch { emit(Request.Error(it)) }