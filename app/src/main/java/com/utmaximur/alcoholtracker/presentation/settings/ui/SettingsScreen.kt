package com.utmaximur.alcoholtracker.presentation.settings.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.settings.SettingsViewModel
import com.utmaximur.alcoholtracker.util.MARKET_APP
import com.utmaximur.alcoholtracker.util.PRIVACY_POLICY
import com.utmaximur.alcoholtracker.util.TERMS_OF_USE

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    darkThemeState: MutableState<Boolean>,
    innerPadding: PaddingValues) {

    val bottomPadding = innerPadding.calculateBottomPadding() + 24.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val isDark = isSystemInDarkTheme()
        val checkedThemeState = remember { mutableStateOf(isDark == darkThemeState.value)}
        val checkedUpdateState by viewModel.checkedUpdateState.observeAsState(true)

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 11.dp
            ) {
                GlideImage(
                    modifier = Modifier
                        .padding(20.dp)
                        .size(128.dp),
                    imageModel = R.mipmap.ic_launcher,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp, 0.dp, 12.dp, 0.dp),
                text = stringResource(id = R.string.settings_version) + " " + BuildConfig.VERSION_NAME,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = MaterialTheme.colors.onPrimary
            )
        }
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 11.dp
        ) {
            Column {
                Switch(R.string.default_theme, checkedThemeState.value) {
                    checkedThemeState.value = it
                    viewModel.onUseDefaultThemeChange(it)
                    if (it && !darkThemeState.value) {
                        darkThemeState.value = it
                    }
                }
                ThemeList(
                    visible = checkedThemeState.value,
                    viewModel = viewModel,
                    darkThemeState = darkThemeState
                )
                Switch(R.string.check_for_updates, checkedUpdateState) {
                    viewModel.onUpdateChange(it)
                }
                Button(R.string.privacy_policy, PRIVACY_POLICY)
                Button(R.string.terms_of_use, TERMS_OF_USE)
                Button(R.string.rate_app, MARKET_APP + LocalContext.current.packageName)
            }
        }
    }
}