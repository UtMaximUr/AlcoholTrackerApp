package com.utmaximur.alcoholtracker.presentation.statistic.ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
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
            .padding(12.dp),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Scaffold(
            modifier = Modifier.heightIn(32.dp, 96.dp),
            content = {
                statisticsCountDays?.let { days -> ViewPagerCountDrink(days) }
            }
        )
    }
}