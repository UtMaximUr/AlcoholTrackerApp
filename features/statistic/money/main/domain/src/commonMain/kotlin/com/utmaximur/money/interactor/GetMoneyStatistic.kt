package com.utmaximur.money.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.statistic.StatisticRepository
import com.utmaximur.money.models.MoneyStatistic
import com.utmaximur.money.models.StatisticPeriod
import com.utmaximur.utils.extensions.instantNow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import org.koin.core.annotation.Factory

@Factory
internal class GetMoneyStatistic(
    dayStatisticRepository: Lazy<StatisticRepository>
) : Interactor<Unit, Flow<List<MoneyStatistic>>>() {

    private val repository by dayStatisticRepository

    override suspend fun doWork(params: Unit) = combine(
        repository.tracksStream,
        repository.currencyStream,
        ::mapPriceListByPeriod
    )

    private fun mapPriceListByPeriod(tracks: List<Track>, currency: String) =
        StatisticPeriod.entries.map { period ->
            mapSumPriceByPeriod(period, tracks, currency)
        }

    private fun mapSumPriceByPeriod(
        period: StatisticPeriod,
        tracks: List<Track>,
        currency: String
    ): MoneyStatistic {
        val moneyAmount = tracks
            .filter { it.date > period.toLocalDate() }
            .map { it.totalPrice }
            .sum()
        return MoneyStatistic(
            statisticPeriod = period,
            moneyAmount = moneyAmount.toString(),
            currency = currency
        )
    }

    private fun StatisticPeriod.toLocalDate(timeZone: TimeZone = TimeZone.UTC) =
        instantNow.minus(1, dateTimeUnit, timeZone).toEpochMilliseconds()
}