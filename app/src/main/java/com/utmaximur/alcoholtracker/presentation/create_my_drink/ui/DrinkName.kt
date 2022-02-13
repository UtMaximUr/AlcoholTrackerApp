package com.utmaximur.alcoholtracker.presentation.create_my_drink.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrinkViewModel
import com.utmaximur.alcoholtracker.util.extension.empty

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrinkName(viewModel: CreateMyDrinkViewModel) {

    val textState = remember { mutableStateOf(String.empty()) }.apply {
        viewModel.onNameDrinkChange(value)
        viewModel.nameState.value?.let { name ->
            value = name
        }
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = textState.value,
        label = { Text(text = stringResource(id = R.string.create_drink_hint)) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_local_bar_white_24dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(colorResource(id = R.color.accent_color))
            )
        },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        onValueChange = {
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
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            })
    )
}