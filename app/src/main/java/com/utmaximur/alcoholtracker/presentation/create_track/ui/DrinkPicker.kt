package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.extension.first
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

    val quantity = viewModel.quantityState.observeAsState(Int.first())

    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = MaterialTheme.colors.onPrimary,
    )
    val possibleValues = (1..10).toList()

    ListItemPicker(
        label = { it.toString() },
        value = quantity.value,
        onValueChange = { viewModel.onQuantityChange(it) },
        list = possibleValues
    )
}

@Composable
fun VolumeNumberPicker(
    text: Int,
    viewModel: CreateTrackViewModel
) {

    val context = LocalContext.current
    val volumeValues: List<String>? =
        viewModel.drinkState.observeAsState().value?.volume?.setVolumeUnit(context)
    val volumeValue = viewModel.volumeState.observeAsState(Int.empty())

    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = MaterialTheme.colors.onPrimary,
    )

    volumeValues?.let { volumes ->
        ListItemPicker(
            label = { it },
            value = volumes[volumeValue.value],
            onValueChange = { viewModel.onVolumeChange(it, volumes.indexOf(it)) },
            list = volumes
        )
    }
}

@Composable
fun DegreeNumberPicker(
    text: Int,
    viewModel: CreateTrackViewModel
) {

    val degreeValues = viewModel.drinkState.observeAsState().value?.degree
    val degreeValue = viewModel.degreeState.observeAsState(Int.empty())

    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = MaterialTheme.colors.onPrimary,
    )

    degreeValues?.let { degrees ->
        ListItemPicker(
            label = { it },
            value = degrees[degreeValue.value],
            onValueChange = { viewModel.onDegreeChange(it, degrees.indexOf(it)) },
            list = degrees
        )
    }
}