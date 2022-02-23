package com.utmaximur.feature_create_track.create_track.ui


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.feature_create_track.create_track.CreateTrackViewModel
import com.utmaximur.utils.SELECT_DAY_ADD
import com.utmaximur.utils.getNavigationResult
import com.utmaximur.utils.removeNavigationResult
import com.utmaximur.feature_create_track.R


@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun CreateTrackerScreen(
    viewModel: CreateTrackViewModel = hiltViewModel(),
    navController: NavHostController,
    editTrackId: String? = null
) {

    val sizeViewPager = remember { mutableStateOf<Dp?>(null) }

    LaunchedEffect(key1 = Unit) {
        editTrackId?.let {
            viewModel.onTrackChange(editTrackId, R.string.edit_drink_title)
        }
        val dateArguments = navController.getNavigationResult<Long>(SELECT_DAY_ADD)
        dateArguments?.let { date ->
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
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                .fillMaxSize(1F)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BoxWithConstraints(
                modifier = if (sizeViewPager.value == null) {
                    Modifier.weight(1f)
                } else {
                    Modifier.wrapContentSize()
                }
            ) {
                if (maxHeight < 220.dp) {
                    sizeViewPager.value = 220.dp
                }
                Box(modifier = Modifier.fillMaxHeight().padding(bottom = 12.dp)) {
                    ViewPagerDrink(viewModel, sizeViewPager.value)
                }
            }
            NumberPickerDrink(viewModel = viewModel)
            CostDrink(viewModel = viewModel)
            ButtonGroup(viewModel = viewModel)
            TotalPrice(viewModel = viewModel)
        }
    }
}