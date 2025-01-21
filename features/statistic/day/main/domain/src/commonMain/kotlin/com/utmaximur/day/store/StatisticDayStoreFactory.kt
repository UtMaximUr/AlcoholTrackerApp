package com.utmaximur.day.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.day.interactor.GetDayStatistic
import com.utmaximur.day.models.DayStatistic
import com.utmaximur.day.store.StatisticDayStore.State
import com.utmaximur.mappers.implementation.RequestMappers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdateState(val request: Request<List<DayStatistic>>) : Message
}

@Factory
internal class StatisticDayStoreFactory(
    storeFactory: StoreFactory,
    interactor: GetDayStatistic
) : StatisticDayStore,
    Store<Nothing, State, Nothing> by storeFactory.create(
        name = StatisticDayStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                launch {
                    interactor.doWork(Unit)
                        .asRequest()
                        .collectLatest { statistic ->
                            dispatch(Message.UpdateState(statistic))
                        }
                }
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