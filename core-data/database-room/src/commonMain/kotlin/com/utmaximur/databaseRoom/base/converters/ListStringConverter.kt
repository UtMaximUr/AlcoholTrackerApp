package com.utmaximur.databaseRoom.base.converters

import androidx.room.TypeConverter

/**
 * Конвертер для ROOM, переводящий список строк в строку и наоборот.
 *
 */
internal class ListStringConverter {

    @TypeConverter
    fun fromString(value: String): List<String> = if (value.isEmpty()) emptyList() else value.split(",")

    @TypeConverter
    fun toString(value: List<String>): String = value.joinToString(",")
}