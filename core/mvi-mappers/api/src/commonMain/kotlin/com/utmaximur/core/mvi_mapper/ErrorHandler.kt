package com.utmaximur.core.mvi_mapper

/**
 * Базовый обработчик ошибок, который используется в mappers для Reducer.
 */
interface ErrorHandler {
    fun handleError(e: Throwable)
}