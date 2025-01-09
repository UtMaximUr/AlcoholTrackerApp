package com.utmaximur.statistic.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.statistic.StatisticComponent


@Composable
internal fun StatisticScreen(
    modifier: Modifier,
    component: StatisticComponent
) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            component.statisticMoneyComponent.Render(Modifier)
            component.statisticDayComponent.Render(Modifier)
            component.statisticDrinkComponent.Render(Modifier)
        }
    }
}