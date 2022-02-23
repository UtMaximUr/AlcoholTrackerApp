package com.utmaximur.feature_create_track.create_track.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.utmaximur.feature_create_track.R
import com.utmaximur.feature_create_track.create_track.CreateTrackViewModel
import com.utmaximur.utils.empty
import com.utmaximur.utils.formatStringTo1f
import com.utmaximur.feature_calculator.calculator.ui.CalculatorScreen

@ExperimentalComposeUiApi
@Composable
fun CostDrink(
    viewModel: CreateTrackViewModel
) {
    Card(
        modifier = Modifier
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Column {
            EventText(viewModel)
            CalculateText(viewModel)
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun EventText(
    viewModel: CreateTrackViewModel
) {

    val textState = remember { mutableStateOf(TextFieldValue(String.empty())) }

    viewModel.eventState.observeAsState().apply {
        value?.let { event -> textState.value = TextFieldValue(event) }
    }

    Row(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EditTextField(
            textState = textState.value.text,
            textId = R.string.add_event,
            leadingIconId = R.drawable.ic_event_24dp,
            leadingIconCDId = R.string.cd_event,
            keyboardType = KeyboardType.Text,
            onValueChange = {
                viewModel.onEventChange(it)
                textState.value = TextFieldValue(it)}
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun CalculateText(
    viewModel: CreateTrackViewModel
) {

    val openDialog = remember { mutableStateOf(false) }
    val textState by viewModel.valueCalculating.observeAsState(String.empty())

    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EditTextField(
            textState = textState.formatStringTo1f(),
            textId = R.string.add_price,
            leadingIconId = R.drawable.ic_local_bar_white_24dp,
            trailingIconId = R.drawable.ic_calculate_white_24dp,
            leadingIconCDId = R.string.cd_price,
            trailingIconCDId = R.string.cd_calculator,
            keyboardType = KeyboardType.Number,
            onClick = { openDialog.value = true },
            onValueChange = {
                viewModel.onPriceChange(it)
                viewModel.onTotalMoneyCalculating(it)
            }
        )
    }

    if (openDialog.value) {
        CalculatorScreen(
            price = textState,
            onDismiss = { openDialog.value = false },
            onResult = { viewModel.onTotalMoneyCalculating(it) }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTextField(
    textState: String,
    textId: Int,
    leadingIconId: Int? = null,
    trailingIconId: Int? = null,
    leadingIconCDId: Int? = null,
    trailingIconCDId: Int? = null,
    keyboardType: KeyboardType,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = textState,
        label = { Text(text = stringResource(id = textId)) },
        leadingIcon = {
            if (leadingIconId != null && leadingIconCDId != null) {
                Image(
                    painter = painterResource(id = leadingIconId),
                    contentDescription = stringResource(id = leadingIconCDId),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }
        },
        trailingIcon = {
            if (trailingIconId != null && trailingIconCDId != null && onClick != null) {
                Image(
                    modifier = Modifier.clickable { onClick() },
                    painter = painterResource(id = trailingIconId),
                    contentDescription = stringResource(id = trailingIconCDId),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        onValueChange = {
            onValueChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
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
