package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.utmaximur.alcohol_calculator.models.DrinksData
import com.utmaximur.design.extensions.bottomFade
import com.utmaximur.design.extensions.fadingEdge
import com.utmaximur.design.extensions.topFade

@Composable
internal fun DrinkList(
    drinksCount: Int,
    drinksData: DrinksData.Builder,
    bottomPadding: Dp
) {
    val lazyColumnState = rememberLazyListState()
    LaunchedEffect(drinksCount) {
        if (drinksCount > 0) {
            lazyColumnState.animateScrollToItem(drinksCount.dec())
        }
    }
    LazyColumn(
        modifier = Modifier
            .fadingEdge(topFade)
            .fadingEdge(bottomFade),
        state = lazyColumnState,
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 12.dp,
            end = 12.dp,
            bottom = bottomPadding
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(count = drinksCount, key = { it }) { index ->
            DrinkItem(
                modifier = Modifier.animateItem(),
                index = index.inc(),
                onVolumeChange = { drinksData.addVolume(index, it) },
                onAlcoholPercentageChange = { drinksData.addAlcoholPercentage(index, it) }
            )
        }
    }
}
