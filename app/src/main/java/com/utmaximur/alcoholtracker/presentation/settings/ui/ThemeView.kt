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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.settings.SettingsViewModel

@Composable
fun ThemeList(visible: Boolean, viewModel: SettingsViewModel) {

    val themeLightCheckState by viewModel.themeLightCheckState.observeAsState()
    val themeDarkCheckState by viewModel.themeDarkCheckState.observeAsState()

    AnimatedVisibility(
        visible = !visible,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column(
            modifier = Modifier.background(colorResource(id = R.color.background_color))
        ) {
            ThemeItem(R.string.light_theme, themeLightCheckState!!) {
                viewModel.onLightThemeChange(it)
            }
            ThemeItem(R.string.dark_theme, themeDarkCheckState!!) {
                viewModel.onDarkThemeChange(it)
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
                contentDescription = null
            )
        }
    }
}