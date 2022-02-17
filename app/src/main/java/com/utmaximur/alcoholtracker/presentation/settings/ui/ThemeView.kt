package com.utmaximur.alcoholtracker.presentation.settings.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.settings.SettingsViewModel

@Composable
fun ThemeList(
    visible: Boolean,
    viewModel: SettingsViewModel,
    darkThemeState: MutableState<Boolean>,
) {

    val themeCheckState = remember { mutableStateOf(darkThemeState.value)}

    AnimatedVisibility(
        visible = !visible,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            ThemeItem(R.string.light_theme, !themeCheckState.value) {
                viewModel.onLightThemeChange(it)
                themeCheckState.value = false
                darkThemeState.value = false
            }
            ThemeItem(R.string.dark_theme, themeCheckState.value) {
                viewModel.onDarkThemeChange(it)
                themeCheckState.value = true
                darkThemeState.value = true
            }
        }
    }
}

@Composable
fun ThemeItem(text: Int, themeCheckState: Boolean, onClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.clickable {
            onClick(!themeCheckState)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            text = stringResource(id = text),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary
        )
        AnimatedVisibility(visible = themeCheckState) {
            Image(
                modifier = Modifier.padding(end = 12.dp),
                painter = painterResource(id = R.drawable.ic_choice_24dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                contentDescription = stringResource(id = R.string.cd_choice_theme)
            )
        }
    }
}