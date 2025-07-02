package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.alcohol_calculator.AlcoholCalculatorComponent
import com.utmaximur.alcohol_calculator.models.DrinksData
import com.utmaximur.design.ui.ElevatedCardApp
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.info
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AlcoholCalculatorScreen(
    modifier: Modifier,
    component: AlcoholCalculatorComponent,
) {
    val state by component.model.collectAsState()
    val drinksData = remember { DrinksData.Builder() }

    Scaffold(
        modifier = modifier,
        floatingActionButton = { FloatingAddDrinkButton(component::onAddDrinkClick) },
        bottomBar = { BottomBarContent { component.onCalculateClick(drinksData.build()) } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
        ) {
            ElevatedCardApp(
                modifier = Modifier.padding(12.dp),
                contentPaddingValues = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                content = { InfoContent(text = stringResource(Res.string.info)) }
            )
            AboutYouContent(
                height = state.height,
                weight = state.weight,
                gender = state.gender,
                changeHeight = component::changeHeight,
                changeWeight = component::changeWeight,
                changeGender = component::changeGender,
            )
            DrinkList(
                drinksCount = state.drinksCount,
                drinksData = drinksData,
                bottomPadding = innerPadding.calculateBottomPadding()
            )
        }
    }
}
