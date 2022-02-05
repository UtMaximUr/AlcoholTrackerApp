package com.utmaximur.alcoholtracker.presentation.statistic.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic
import com.utmaximur.alcoholtracker.presentation.statistic.StatisticViewModel
import com.utmaximur.alcoholtracker.util.getIdRaw

@ExperimentalFoundationApi
@Composable
fun CountDrinksStatistic(viewModel: StatisticViewModel) {

    val drinksList by viewModel.statisticsDrinksList.observeAsState()

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(22.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                text = stringResource(id = R.string.statistic_drinks),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
            LazyVerticalGrid(
                modifier = Modifier.padding(12.dp),
                cells = GridCells.Fixed(4)
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
                contentDescription = null
            )
            Text(
                maxLines = 1,
                text = String.format(
                    context.resources.getString(R.string.statistic_count_drink),
                    drink.count
                ),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
        }
        Text(
            text = drink.drink,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
    }
}