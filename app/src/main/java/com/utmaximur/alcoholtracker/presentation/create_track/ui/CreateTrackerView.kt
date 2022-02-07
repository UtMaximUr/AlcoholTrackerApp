package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun CreateTrackerView(
    viewModel: CreateTrackViewModel,
    onSaveClick: () -> Unit,
    onCalculateClick: (String) -> Unit
) {

    viewModel.saveState.observeAsState().apply {
        if (value == true) onSaveClick()
    }

    Column {
        ToolBar(
            viewModel = viewModel
        )
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(1F)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                ViewPagerDrink(viewModel)
            }
            NumberPickerDrink(viewModel = viewModel)
            CostDrink(viewModel = viewModel) { price ->
                onCalculateClick(price)
            }
            ButtonGroup(viewModel = viewModel)
            TotalPrice(viewModel = viewModel)
        }
    }
}