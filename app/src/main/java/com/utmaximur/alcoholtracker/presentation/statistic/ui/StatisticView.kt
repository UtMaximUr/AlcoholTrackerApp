package com.utmaximur.alcoholtracker.presentation.statistic.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.presentation.statistic.StatisticViewModel

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun StatisticView(viewModel: StatisticViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountMoneyStatistic(viewModel)
        CountDaysStatistic(viewModel)
        CountDrinksStatistic(viewModel)
    }
}