package com.utmaximur.drink.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.ZERO_VALUE
import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.statistic.StatisticRepository
import com.utmaximur.drink.model.DrinkStatistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.koin.core.annotation.Factory

@Factory
internal class GetMoneyStatistic(
    dayStatisticRepository: Lazy<StatisticRepository>
) : Interactor<Unit, Flow<List<DrinkStatistic>>>() {

    private val repository by dayStatisticRepository

    override fun doWork(params: Unit) = combine(
        repository.tracksStream,
        repository.drinksStream,
        ::mapDrinks
    )

    private fun mapDrinks(tracks: List<Track>, drinks: List<Drink>) =
        drinks.map { drink ->
            val countsDrink = tracks.find { it.drink == drink }?.quantity
            DrinkStatistic(
                name = drink.name,
                icon = drink.icon,
                countsDrink = countsDrink ?: ZERO_VALUE
            )
        }
}