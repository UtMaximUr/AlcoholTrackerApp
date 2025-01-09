package com.utmaximur.core.mvi_mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Расширение для Flow, переводящее асинхронный запрос загрузки данных к Flow<[Request]>.
 *
 * При добавлении к цепочке flow, необходимо применять именно к тому элементу, который будет эмитить значения.
 */
fun <T> Flow<T>.asRequest(): Flow<Request<T>> = this
    .map { Request.Success(it) as Request<T> }
    .onStart { emit(Request.Loading()) }
    .catch { emit(Request.Error(it)) }