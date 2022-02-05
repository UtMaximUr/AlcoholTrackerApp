package com.utmaximur.alcoholtracker.presentation.statistic.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
            .fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 0.dp),
                text = stringResource(id = R.string.statistic_spent),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
            Scaffold(
                modifier = Modifier.heightIn(32.dp, 96.dp),
                content = {
                    statisticsPriceByPeriod?.let { money -> ViewPagerCountMoney(money) }
                }
            )
        }
    }
}