package com.utmaximur.alcoholtracker.presentation.statistic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.statistic.StatisticViewModel

@ExperimentalPagerApi
@Composable
fun CountMoneyStatistic(viewModel: StatisticViewModel) {

    val statisticsPriceByPeriod by viewModel.statisticsPriceByPeriod.observeAsState()

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight(0.15f)
            .fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 11.dp
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 0.dp),
                text = stringResource(id = R.string.statistic_spent),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = MaterialTheme.colors.onPrimary
            )
            statisticsPriceByPeriod?.let { money -> ViewPagerCountMoney(money) }
        }
    }
}