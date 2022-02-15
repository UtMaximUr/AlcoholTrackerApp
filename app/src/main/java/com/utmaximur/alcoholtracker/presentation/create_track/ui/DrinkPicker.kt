package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.setVolumeUnit

@Composable
fun NumberPickerDrink(
    viewModel: CreateTrackViewModel
) {
    Card(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .wrapContentSize(),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuantityNumberPicker(
                    text = R.string.add_quantity,
                    viewModel = viewModel
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VolumeNumberPicker(
                    text = R.string.add_volume,
                    viewModel = viewModel
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DegreeNumberPicker(
                    text = R.string.add_degree,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun QuantityNumberPicker(text: Int, viewModel: CreateTrackViewModel) {

    val state = remember { mutableStateOf(Int.empty()) }.apply {
        value.let { value ->
            viewModel.onQuantityChange(value)
            viewModel.onTotalMoneyCalculating(value)
        }
    }

    viewModel.quantityState.observeAsState().apply {
        value?.let { quantity -> state.value = quantity }
    }

    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = colorResource(id = R.color.text_color)
    )
    val possibleValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    ListItemPicker(
        label = { it.toString() },
        value = state.value,
        onValueChange = { state.value = it.toString().toInt() },
        list = possibleValues
    )
}

@Composable
fun VolumeNumberPicker(
    text: Int,
    viewModel: CreateTrackViewModel
) {

    val context = LocalContext.current
    val volumeState by viewModel.volumeState.observeAsState()
    val drinkState by viewModel.drinkState.observeAsState()

    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = colorResource(id = R.color.text_color)
    )
    if (drinkState != null) {
        val possibleValues: List<String> = drinkState!!.volume.setVolumeUnit(context)
        var state by remember {
            mutableStateOf(possibleValues[volumeState!!])
        }.apply {
            viewModel.onVolumeChange(value)
        }

        ListItemPicker(
            label = { it },
            value = state,
            onValueChange = { state = it },
            list = possibleValues
        )
    }
}

@Composable
fun DegreeNumberPicker(
    text: Int,
    viewModel: CreateTrackViewModel
) {

    val drinkState by viewModel.drinkState.observeAsState()
    val degreeState by viewModel.degreeState.observeAsState()

    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = colorResource(id = R.color.text_color)
    )

    if (drinkState != null) {
        val possibleValues: List<String?> = drinkState!!.degree
        var state by remember {
            mutableStateOf(possibleValues[degreeState!!])
        }.apply {
            viewModel.onDegreeChange(value.toString())
        }
        ListItemPicker(
            label = { it.toString() },
            value = state,
            onValueChange = { state = it },
            list = possibleValues
        )
    }
}