package com.utmaximur.alcoholtracker.presentation.statistic.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.presentation.statistic.StatisticViewModel

@ExperimentalPagerApi
@Composable
fun CountDaysStatistic(viewModel: StatisticViewModel) {

    val statisticsCountDays by viewModel.statisticsCountDays.observeAsState()

    Card(
        modifier = Modifier
            .fillMaxHeight(0.15f)
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(22.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 11.dp
    ) {
        statisticsCountDays?.let { days -> ViewPagerCountDrink(days) }
    }
}