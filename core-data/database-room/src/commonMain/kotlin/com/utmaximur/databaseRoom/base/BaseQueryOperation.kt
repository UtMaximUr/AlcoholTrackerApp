package com.utmaximur.databaseRoom.base

import kotlinx.coroutines.flow.Flow

interface BaseQueryOperation<out T> {

    /**
     * Получить [Flow] со списком всех значений типа T.
     *
     * @return [Flow] со списком всех значений типа T.
     */
    fun getAllFlow(): Flow<List<T>> {
        throw NotImplementedError("override me")
    }

    /**
     * Получить список всех значений типа T.
     *
     * @return список всех значений типа T.
     */
    suspend fun getAll(): List<T> {
        throw NotImplementedError("override me")
    }

    /**
     * Получить значение типа T.
     *
     * @param id идентификатор значения типа T.
     * @return словарь.
     */
    suspend fun get(id: Int): T? {
        throw NotImplementedError("override me")
    }

    /**
     * Получить значение типа T.
     *
     * @param id идентификатор значения типа T.
     * @return [Flow] типа T.
     */
    fun getFlow(id: Int): Flow<T> {
        throw NotImplementedError("override me")
    }

    /**
     * Удалить значение типа T по его идентификатору.
     *
     * @param id идентификатор значения типа T.
     */
    suspend fun delete(id: Int) {
        throw NotImplementedError("override me")
    }
}