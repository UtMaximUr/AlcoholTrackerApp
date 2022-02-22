package com.utmaximur.feature_calendar.calendar.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import com.utmaximur.feature_calendar.R
import com.utmaximur.feature_calendar.calendar.CalendarViewModel
import com.utmaximur.feature_calendar.calendar.state.TextState
import com.utmaximur.utils.empty
import com.utmaximur.utils.zero

@Composable
fun InfoText(viewModel: CalendarViewModel = hiltViewModel()) {

    val textState = remember {
        mutableStateOf(Int.zero())
    }

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


    Text(
        text = if (textState.value != Int.zero()) stringResource(id = textState.value) else String.empty(),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular))
    )
}