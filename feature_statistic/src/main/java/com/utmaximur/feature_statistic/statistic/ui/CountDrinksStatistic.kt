package com.utmaximur.feature_statistic.statistic.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.utmaximur.feature_statistic.statistic.StatisticViewModel
import com.utmaximur.domain.entity.DrinkStatistic
import com.utmaximur.feature_statistic.R
import com.utmaximur.utils.getIdRaw
import com.utmaximur.utils.getResString

@ExperimentalFoundationApi
@Composable
fun CountDrinksStatistic(viewModel: StatisticViewModel) {

    val drinksList by viewModel.statisticsDrinksList.observeAsState()

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(22.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 11.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                text = stringResource(id = R.string.statistic_drinks),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = MaterialTheme.colors.onPrimary
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                cells = GridCells.Fixed(4),
                verticalArrangement = Arrangement.Center
            ) {
                drinksList?.let { drinks ->
                    items(drinks.size) { index ->
                        DrinkItem(drinks[index])
                    }
                }
            }
        }
    }
}

@Composable
fun DrinkItem(
    drink: DrinkStatistic,
    context: Context = LocalContext.current
) {
    Column(
        modifier = Modifier.padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (verticalAlignment = Alignment.CenterVertically){
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = drink.icon.getIdRaw(context)),
                contentDescription = drink.drink.getResString(context)
            )
            Text(
                maxLines = 1,
                text = stringResource(id = R.string.statistic_count_drink, drink.count),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = MaterialTheme.colors.onPrimary
            )
        }
        Text(
            text = drink.drink.getResString(context = context).orEmpty(),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary
        )
    }
}