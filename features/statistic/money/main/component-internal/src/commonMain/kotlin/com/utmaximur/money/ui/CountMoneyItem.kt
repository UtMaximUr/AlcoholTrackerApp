package com.utmaximur.money.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            text = stringResource(
                Res.allStringResources.getStringResource(
                    idResource = statistic.statisticPeriod.raw
                )
            )
        )
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            text = statistic.moneyAmount
        )
        Text(
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyMedium,
            text = statistic.currency
        )
    }
}