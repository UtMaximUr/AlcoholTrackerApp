package com.utmaximur.feature_create_drink.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.feature_create_drink.R
import com.utmaximur.feature_create_drink.CreateMyDrinkViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrinkVolume(
    context: Context = LocalContext.current,
    viewModel: CreateMyDrinkViewModel
) {

    val volumeState = viewModel.getVolumes(context)
    val volumeListState by viewModel.volumeListState.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(text = stringResource(id = R.string.dialog_create_drink_add_volume))
        Spacer(modifier = Modifier.padding(top = 16.dp))
        LazyColumn {
            items(count = volumeState.size) { index ->
                VolumeItem(volumeState[index], volumeListState) {
                    viewModel.onVolumeChange(it)
                }
            }
        }
    }
}

@Composable
fun VolumeItem(
    volume: String,
    selectedVolume: List<String?>?,
    onChecked: (String) -> Unit
) {

    val volumeState = remember { mutableStateOf(false) }.apply {
        selectedVolume?.forEach {
            if (it == volume) {
                value = true
            }
        }
    }

    Row(
        modifier = Modifier.clickable {
            volumeState.value = !volumeState.value
            onChecked(volume)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            text = volume,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        Checkbox(
            checked = volumeState.value,
            enabled = false,
            colors = CheckboxDefaults.colors(
                disabledColor = MaterialTheme.colors.primary
            ),
            onCheckedChange = {}
        )
    }
}