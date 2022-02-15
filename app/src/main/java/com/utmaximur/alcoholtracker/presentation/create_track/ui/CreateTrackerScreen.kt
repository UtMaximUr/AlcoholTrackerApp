package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel
import com.utmaximur.alcoholtracker.util.SELECT_DAY_ADD
import com.utmaximur.alcoholtracker.util.getNavigationResult
import com.utmaximur.alcoholtracker.util.removeNavigationResult


@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun CreateTrackerScreen(
    viewModel: CreateTrackViewModel = hiltViewModel(),
    navController: NavHostController,
    editTrackId: String? = null) {


    LaunchedEffect(key1 = Unit) {
        editTrackId?.let {
            viewModel.onTrackChange(editTrackId, R.string.edit_drink_title)
        }
        val dateArguments = navController.getNavigationResult<Long>(SELECT_DAY_ADD)
        dateArguments?.let {  date ->
            viewModel.onDateChange(date)
            navController.removeNavigationResult<Long>(SELECT_DAY_ADD)
        }
    }

    Column {
        ToolBar(
            viewModel = viewModel,
            navController = navController
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
            CostDrink(viewModel = viewModel)
            ButtonGroup(viewModel = viewModel)
            TotalPrice(viewModel = viewModel)
        }
    }
}