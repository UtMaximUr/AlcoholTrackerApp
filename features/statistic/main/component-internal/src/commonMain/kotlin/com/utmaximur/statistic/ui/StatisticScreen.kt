package com.utmaximur.statistic.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.statistic.StatisticComponent

@Composable
internal fun StatisticScreen(
    modifier: Modifier,
    component: StatisticComponent,
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                val rowModifier = remember { Modifier.weight(1f).aspectRatio(1f) }
                component.statisticMoneyComponent.Render(rowModifier)
                component.statisticDayComponent.Render(rowModifier)
            }
            component.statisticDrinkComponent.Render(Modifier)
        }
    }
}
