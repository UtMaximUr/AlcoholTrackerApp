package com.utmaximur.day.interactor

import com.utmaximur.day.models.DayStatistic
import com.utmaximur.day.models.StatisticDay
import com.utmaximur.domain.Interactor
import com.utmaximur.domain.ZERO_VALUE
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.statistic.StatisticRepository
import com.utmaximur.utils.extensions.instantNow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Factory

@Factory
internal class GetDayStatistic(
    dayStatisticRepository: Lazy<StatisticRepository>
) : Interactor<Unit, Flow<List<DayStatistic>>>() {

    private val repository by dayStatisticRepository

    override fun doWork(params: Unit): Flow<List<DayStatistic>> =
        repository.tracksStream.map(::mapStatisticCountDays)

    private fun mapStatisticCountDays(tracks: List<Track>) = buildList {
        add(getCountsDaysInYear(tracks))
        add(getCountsDaysSinceTheLastDrink(tracks))
    }

    private fun getCountsDaysInYear(tracks: List<Track>): DayStatistic {
        val localDateTimeNow = instantNow.toLocalDateTime(TimeZone.UTC)
        val lastLocalDateInYear = LocalDate(localDateTimeNow.year, 12, 31)
        val countsDaysInYear = lastLocalDateInYear.dayOfYear
        return DayStatistic(
            statisticDay = StatisticDay.COUNT_DAYS,
            countsDays = tracks.groupBy { it.date }.size,
            countsDaysInYear = countsDaysInYear
        )
    }

    private fun getCountsDaysSinceTheLastDrink(tracks: List<Track>): DayStatistic {
        val firstDate = tracks.groupBy { it.date }.keys.firstOrNull()
        firstDate ?: return DayStatistic(
            statisticDay = StatisticDay.COUNT_DAYS_NO_DRINK,
            countsDays = ZERO_VALUE
        )
        val firstDateInstant = Instant.fromEpochMilliseconds(firstDate)
        val period = firstDateInstant.periodUntil(instantNow, TimeZone.UTC)
        return DayStatistic(
            statisticDay = StatisticDay.COUNT_DAYS_NO_DRINK,
            countsDays = period.days
        )
    }
}