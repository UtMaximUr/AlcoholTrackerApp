package com.utmaximur.alcoholtracker.presentation.create_my_drink.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrinkViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrinkVolume(
    context: Context = LocalContext.current,
    viewModel: CreateMyDrinkViewModel
) {

    val volumeState = viewModel.getVolumes(context)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.dialog_create_drink_add_volume),
            color = colorResource(id = R.color.text_color),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        LazyColumn {
            items(count = volumeState.size) { index ->
                VolumeItem(volumeState[index]) {
                    viewModel.onVolumeChange(it)
                }
            }
        }
    }
}

@Composable
fun VolumeItem(
    volume: String,
    onChecked: (String) -> Unit
) {

    val volumeState = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.clickable {
            volumeState.value = !volumeState.value
            onChecked(volume)
        }
    ) {
        Text(
            text = volume,
            modifier = Modifier.weight(1f),
            color = colorResource(id = R.color.text_color),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            fontSize = 16.sp
        )
        Checkbox(
            checked = volumeState.value,
            enabled = false,
            colors = CheckboxDefaults.colors(
                disabledColor = colorResource(id = R.color.accent_color)
            ),
            onCheckedChange = {}
        )
    }
}