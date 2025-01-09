package com.utmaximur.drink.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.drink.analytic_events.OpenScreenEvent
import com.utmaximur.drink.interactor.GetMoneyStatistic
import com.utmaximur.drink.model.DrinkStatistic
import com.utmaximur.drink.store.StatisticDrinkStore.State
import com.utmaximur.mappers.implementation.RequestMappers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdateState(val request: Request<List<DrinkStatistic>>) : Message
}

@Factory
internal class StatisticDrinkStoreFactory(
    storeFactory: StoreFactory,
    interactor: GetMoneyStatistic,
    analyticsManager: AnalyticsManager
) : StatisticDrinkStore,
    Store<Nothing, State, Nothing> by storeFactory.create(
        name = StatisticDrinkStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                launch { analyticsManager.trackEvent(OpenScreenEvent()) }
                interactor.doWork(Unit)
                    .asRequest()
                    .onEach { statistic -> dispatch(Message.UpdateState(statistic)) }
                    .launchIn(this)
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateState -> {
                    val newRequestUi = RequestMapper.builder(message.request)
                        .mapData(RequestMappers.data.emptyListToNull())
                        .mapLoading(RequestMappers.loading.default())
                        .build()
                    copy(requestUi = newRequestUi)
                }
            }
        }
    )