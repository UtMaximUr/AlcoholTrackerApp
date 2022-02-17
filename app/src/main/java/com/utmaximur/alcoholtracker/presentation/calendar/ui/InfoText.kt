package com.utmaximur.alcoholtracker.presentation.calendar.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.calendar.CalendarViewModel
import com.utmaximur.alcoholtracker.presentation.calendar.state.TextState
import com.utmaximur.alcoholtracker.util.extension.empty
import timber.log.Timber

@Composable
fun InfoText(viewModel: CalendarViewModel = hiltViewModel()) {

    val textState = remember { mutableStateOf(Int.empty()) }

    viewModel.textState.observeAsState()
        .apply {
            value?.let { state ->
                when (state) {
                    is TextState.AddPressToStart -> textState.value = state.textId
                    is TextState.SelectDay -> textState.value = state.textId
                    is TextState.TracksEmpty -> textState.value = state.textId
                }
            }
        }


    if (textState.value != Int.empty()) {
        Text(
            text = stringResource(id = textState.value),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        )
    }

    Timber.tag("calendar_debug_log")
    Timber.d("InfoText")
}