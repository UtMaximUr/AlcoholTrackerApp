package com.utmaximur.feature_statistic.statistic.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.feature_statistic.statistic.StatisticViewModel
import com.utmaximur.feature_statistic.statistic.ui.CountDaysStatistic
import com.utmaximur.feature_statistic.statistic.ui.CountDrinksStatistic
import com.utmaximur.feature_statistic.statistic.ui.CountMoneyStatistic


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun StatisticScreen(viewModel: StatisticViewModel = hiltViewModel(), innerPadding: Dp) {

    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = innerPadding),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountMoneyStatistic(viewModel)
        CountDaysStatistic(viewModel)
        CountDrinksStatistic(viewModel)
    }
}