package com.utmaximur.databaseRoom.base.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Конвертер для ROOM, переводящий даты в Long и наоборот.
 *
 */

internal class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): Instant = Instant.fromEpochMilliseconds(value)

    @TypeConverter
    fun dateToTimestamp(date: Instant): Long = date.toEpochMilliseconds()
}

internal class LocalDateConverter {
    @TypeConverter
    fun fromTimestamp(databaseValue: String): LocalDate = if (databaseValue.isEmpty()) {
        val currentMoment = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        currentMoment.date
    } else {
        LocalDate.parse(databaseValue)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate): String = date.toString()
}

internal class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(databaseValue: String): LocalDateTime = if (databaseValue.isEmpty()) {
        LocalDateTime.now()
    } else {
        LocalDateTime.parse(databaseValue)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime): String = date.toString()

    private fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
        return Clock.System.now().toLocalDateTime(timeZone)
    }

}