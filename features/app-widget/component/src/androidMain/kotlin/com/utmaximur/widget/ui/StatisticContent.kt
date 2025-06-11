package com.utmaximur.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.design.RequestWidget
import com.utmaximur.money.store.StatisticMoneyStore
import com.utmaximur.widget.mapper.toMoneyStatisticUi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun StatisticContent(store: StatisticMoneyStore) {
    val context = LocalContext.current
    val state by store.stateFlow.collectAsState()

    RequestWidget(
        state = state.requestUi
    ) { items ->
        Column(
            modifier = GlanceModifier.padding(vertical = 16.dp)
        ) {
            items
                .map { it.toMoneyStatisticUi() }
                .forEach { item ->
                    MoneyStatisticItem(
                        title = context.getString(item.titleResId),
                        value = item.formatAmount,
                    )
                }
        }
    }
}