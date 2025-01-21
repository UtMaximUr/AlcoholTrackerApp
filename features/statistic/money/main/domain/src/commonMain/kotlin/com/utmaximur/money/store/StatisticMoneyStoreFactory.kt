package com.utmaximur.money.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.mappers.implementation.RequestMappers
import com.utmaximur.money.interactor.GetMoneyStatistic
import com.utmaximur.money.models.MoneyStatistic
import com.utmaximur.money.store.StatisticMoneyStore.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdateState(val request: Request<List<MoneyStatistic>>) : Message
}

@Factory
internal class StatisticMoneyStoreFactory(
    storeFactory: StoreFactory,
    interactor: GetMoneyStatistic
) : StatisticMoneyStore,
    Store<Nothing, State, Nothing> by storeFactory.create(
        name = StatisticMoneyStore::class.simpleName,
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