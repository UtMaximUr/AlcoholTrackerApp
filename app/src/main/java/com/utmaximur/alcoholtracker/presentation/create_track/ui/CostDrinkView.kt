package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.calculator.ui.CalculatorScreen
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.formatStringTo1f

@ExperimentalComposeUiApi
@Composable
fun CostDrink(
    viewModel: CreateTrackViewModel
) {
    Card(
        modifier = Modifier
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Column {
            EventText(R.string.add_event, KeyboardType.Text, viewModel)
            CalculateText(R.string.add_price, viewModel)
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun EventText(
    text: Int,
    keyboardType: KeyboardType,
    viewModel: CreateTrackViewModel
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val event by viewModel.eventState.observeAsState()
    val textState = remember { mutableStateOf(TextFieldValue(event ?: String.empty())) }

    Row(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = textState.value,
            label = { Text(text = stringResource(id = text)) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_event_24dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.accent_color))
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                viewModel.onEventChange(it.text)
                textState.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.text_color),
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = colorResource(id = R.color.accent_color),
                unfocusedIndicatorColor = colorResource(id = R.color.accent_color),
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun CalculateText(
    text: Int,
    viewModel: CreateTrackViewModel
) {

    val openDialog = remember { mutableStateOf(false) }
    val textState by viewModel.valueCalculating.observeAsState(String.empty())
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = textState.formatStringTo1f(),
            label = { Text(text = stringResource(id = text)) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_local_bar_white_24dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.accent_color))
                )
            },
            trailingIcon = {
                Image(
                    modifier = Modifier.clickable { openDialog.value = true }, //onCalculateClick(textState) },
                    painter = painterResource(id = R.drawable.ic_calculate_white_24dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.accent_color))
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                viewModel.onPriceChange(it)
                viewModel.onTotalMoneyCalculating(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.text_color),
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = colorResource(id = R.color.accent_color),
                unfocusedIndicatorColor = colorResource(id = R.color.accent_color),
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
        )
    }

    if (openDialog.value) {
        CalculatorScreen(
            price = textState,
            onDismiss = { openDialog.value = false },
            onResult = { viewModel.onTotalMoneyCalculating(it)}
        )
    }
}