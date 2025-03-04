package com.utmaximur.money.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.text.getStringResource
import com.utmaximur.money.models.MoneyStatistic
import money.resources.Res
import money.resources.allStringResources
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun CountMoneyItem(statistic: MoneyStatistic) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(
                Res.allStringResources.getStringResource(
                    idResource = statistic.statisticPeriod.raw
                )
            )
        )
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = statistic.moneyAmount
            )
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = statistic.currency
            )
        }
    }
}