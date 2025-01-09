package com.utmaximur.day.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.utmaximur.day.models.DayStatistic
import com.utmaximur.design.text.getStringResource
import day.resources.Res
import day.resources.allStringResources
import day.resources.plurals_day
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun DrinksDayItem(dayStatistic: DayStatistic) {
    val counts = pluralStringResource(
        Res.plurals.plurals_day,
        dayStatistic.countsDays,
        dayStatistic.countsDays
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        text = stringResource(
            Res.allStringResources.getStringResource(
                idResource = dayStatistic.statisticDay.raw
            ), counts, dayStatistic.countsDaysInYear
        )
    )
}