package com.utmaximur.drink.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.drink.StatisticDrinkComponent
import drinkStatistic.resources.Res
import drinkStatistic.resources.statistic_drinks
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun StatisticDrinkScreen(
    component: StatisticDrinkComponent
) {
    val state by component.model.collectAsState()

    ElevatedCardApp(
        modifier = Modifier.fillMaxWidth()
    ) {
        RequestWidget(
            state = state.requestUi,
            shimmerContentTemplate = { DrinksShimmer() }
        ) { statistics ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(Res.string.statistic_drinks)
                )
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.SpaceAround,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(statistics) { drink ->
                        DrinkItem(drink)
                    }
                }
            }
        }
    }
}