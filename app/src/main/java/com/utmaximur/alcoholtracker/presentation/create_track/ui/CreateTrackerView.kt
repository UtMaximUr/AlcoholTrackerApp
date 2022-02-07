package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackFragment
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel
import com.utmaximur.alcoholtracker.util.formatDate


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun CreateTrackerView(
    viewModel: CreateTrackViewModel,
    addFragmentListener: CreateTrackFragment.AddFragmentListener?,
    onSaveClick: () -> Unit,
    onCalculateClick: (String) -> Unit
) {

    val totalMoneyState by viewModel.totalMoney.observeAsState()
    val titleState by viewModel.titleFragment.observeAsState()

    var dateDefaultText = stringResource(id = R.string.add_date)
    viewModel.selectedDate.observeAsState().apply {
        value?.let { date -> dateDefaultText = date.formatDate(LocalContext.current) }
    }
    val dateState by viewModel.dateState.observeAsState(dateDefaultText)
    val visibleTodayState by viewModel.visibleTodayState.observeAsState(true)

    viewModel.saveState.observeAsState().apply {
        if (value == true) onSaveClick()
    }
    viewModel.closeState.observeAsState().apply {
        if (value == true) addFragmentListener?.closeFragment()
    }

    Column {
        ToolBar(
            viewModel = viewModel,
            title = titleState,
            onBackClick = { addFragmentListener?.closeFragment() },
            onSaveClick = { viewModel.onSaveButtonClick() },
            onCreateClick = { addFragmentListener?.onShowAddNewDrinkFragment() },
            onEditClick = { viewModel.onEditClick() },
            onDeleteClick = { viewModel.onDeleteClick() }
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
            ButtonGroup(
                dateState = dateState,
                visibleTodayState = visibleTodayState,
                onSelectDateClick = { viewModel.onSelectDayClick() },
                onTodayClick = { viewModel.onTodayClick() })
            TotalPrice(totalMoneyState)
        }
    }
}