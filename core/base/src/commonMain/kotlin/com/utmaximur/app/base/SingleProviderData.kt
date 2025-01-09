package com.utmaximur.app.base

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

/**
 * Интерфейс определяющий контракт процесса обработки одиночных данных.
 *
 * Интерфейс предоставляет функциональность для отправки данных и получения потока данных.
 *
 * @param T Тип данных, с которым работает процесс. Он может быть любым типом данных.
 */
interface SingleDataProcess<T> {
    /**
     * Flow данных типа [T], который может быть наблюдаемым.
     * Предоставляет поток данных, которые были отправлены.
     * Null значения фильтруются и не публикуются в потоке.
     */
    val dataFlow: Flow<T>

    /**
     * Отправляет данные в поток.
     *
     * @param data Данные для отправки. Должны соответствовать типу [T].
     */
    suspend fun sendData(data: T)
}

/**
 * Базовая реализация [SingleDataProcess] для предоставления функциональности по управлению одиночными данными.
 *
 * Использует [MutableStateFlow] для удержания и публикации данных.
 *
 * @param T Тип данных, с которым работает провайдер. Он может быть любым типом данных.
 */
abstract class BaseSingleProviderData<T>(
    private val mutableFlowWrapper: MutableFlowWrapper<T> = MutableStateFlowWrapper()
) : SingleDataProcess<T> {

    /**
     * [dataFlow] фильтрует null значения и предоставляет только ненулевые данные.
     * Позволяет подписчикам получать данные, которые были явно отправлены через [sendData].
     */
    override val dataFlow: Flow<T> = mutableFlowWrapper.data

    /**
     * Пробует отправить [data] в [mutableFlowWrapper].
     * Если эмиссия происходит успешно, данные становятся доступными подписчикам [dataFlow].
     *
     * @param data Данные для отправки. Должны соответствовать типу [T].
     */
    override suspend fun sendData(data: T) = mutableFlowWrapper.emit(data)
}

interface MutableFlowWrapper<T> {

    val data: Flow<T>

    suspend fun emit(data: T)
}

class MutableStateFlowWrapper<T>(initial: T? = null) : MutableFlowWrapper<T> {

    private val mutableFlow = MutableStateFlow(initial)

    override val data: Flow<T> = mutableFlow.filterNotNull()

    override suspend fun emit(data: T) = mutableFlow.update { data }
}

class MutableSharedFlowWrapper<T>(
    replay: Int = 0,
    extraBufferCapacity: Int = 0,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : MutableFlowWrapper<T> {
    private val mutableFlow = MutableSharedFlow<T>(replay, extraBufferCapacity, onBufferOverflow)

    override val data: Flow<T> = mutableFlow.filterNotNull()

    override suspend fun emit(data: T) = mutableFlow.emit(data)
}