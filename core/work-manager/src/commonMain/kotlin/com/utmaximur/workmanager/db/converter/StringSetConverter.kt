package com.utmaximur.workmanager.db.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.encodeToString

@TypeConverters
internal class StringSetConverter {
    @TypeConverter
    fun typeFromJson(value: String): Set<String> = json.decodeFromString(value)

    @TypeConverter
    fun typeToJson(list: Set<String>): String = json.encodeToString(list)

}