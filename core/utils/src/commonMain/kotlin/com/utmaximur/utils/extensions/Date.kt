package com.utmaximur.utils.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


private val dateFormat_DD_MM_YYYY = LocalDate.Format {
    dayOfMonth()
    char('/')
    monthNumber(padding = Padding.SPACE)
    char(' ')
    year()
}

val instantNow = Clock.System.now()

fun String?.parseToLong(): Long? {
    this?.ifEmpty { return null }
    return this?.parseToLongNotNull()
}

fun String.parseToLongNotNull(): Long {
    val date = this.ifEmpty { Clock.System.now().toLocalDate() }
    val localDate = dateFormat_DD_MM_YYYY.parse(date)
    val localDateTime = LocalDateTime(localDate, LocalTime(0, 0))
    val timeZone: TimeZone = TimeZone.UTC
    val instant = localDateTime.toInstant(timeZone)
    return instant.toEpochMilliseconds()
}

fun Long.toDateUi(timeZone: TimeZone = TimeZone.UTC): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDate = instant.toLocalDateTime(timeZone).date
    return localDate.format(dateFormat_DD_MM_YYYY)
}

fun Long.toLocalDate(timeZone: TimeZone = TimeZone.UTC): LocalDate {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDate = instant.toLocalDateTime(timeZone).date
    return localDate
}

fun Instant?.toLocalDate(): String {
    val localDate = this?.toLocalDateTime(TimeZone.UTC)?.date
        ?: Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    return localDate.format(dateFormat_DD_MM_YYYY)
}

fun getTodayDateUi(timeZone: TimeZone = TimeZone.UTC): String {
    val nowDate = Clock.System.now().toLocalDateTime(timeZone).date
    return nowDate.format(dateFormat_DD_MM_YYYY)
}
