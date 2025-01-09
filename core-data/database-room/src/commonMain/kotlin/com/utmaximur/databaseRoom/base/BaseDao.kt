package com.utmaximur.databaseRoom.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

/**
 * Интерфейс для базовых операций в DAO.
 *
 * @param T тип оперируемых элементов.
 */
interface BaseDao<T> {

    /**
     * Добавить элемент в БД.
     *
     * @param item элемент для добавления.
     * @return rowId вставленного элемента. Если -1 - значит элемент
     * с таким первичным ключом уже есть в базе.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: T): Long

    /**
     * Добавить элементы в БД.
     *
     * @param items элементы для добавления.
     * @return массив с rowId вставленных элементов.
     * Если -1 стоит на позиции элемента в переданном массиве [items] - значит элемент
     * с таким первичным ключом уже есть в базе.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<T>): List<Long>

    /**
     * обновить элемент в БД.
     *
     * @param item элемент для обновления.
     * Поиск подходящего элемента в БД происходит по первичному ключу.
     */
    @Update
    suspend fun update(item: T)

    /**
     * обновить элементы в БД.
     *
     * @param items элементы для обновления.
     * Поиск подходящего элемента в БД происходит по первичному ключу.
     */
    @Update
    suspend fun update(items: List<T>)

    /**
     * Добавить элемент или обновить уже имеющиеся
     * @param item элемент для добавления.
     */
    @Upsert
    suspend fun upsert(item: T)

    /**
     * Добавить или изменить уже имеющиеся список сущностей.
     *
     * @param items список сущностей.
     */
    @Upsert
    suspend fun upsert(items: List<T>)

    /**
     * удалить элемент из БД.
     *
     * @param item элемент для удаления.
     * Поиск подходящего элемента в БД происходит по первичному ключу.
     */
    @Delete
    suspend fun delete(item: T)

    /**
     * удалить элементы из БД.
     *
     * @param items элементы для удаления.
     * Поиск подходящего элемента в БД происходит по первичному ключу.
     */
    @Delete
    suspend fun delete(items: List<T>)

}