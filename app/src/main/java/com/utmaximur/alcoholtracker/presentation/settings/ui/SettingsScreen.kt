package com.utmaximur.alcoholtracker.presentation.settings.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.settings.SettingsViewModel
import com.utmaximur.alcoholtracker.util.MARKET_APP
import com.utmaximur.alcoholtracker.util.PRIVACY_POLICY
import com.utmaximur.alcoholtracker.util.TERMS_OF_USE
import timber.log.Timber

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    darkThemeState: MutableState<Boolean>,
    innerPadding: Dp) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = innerPadding),
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
                        .background(MaterialTheme.colors.primary)
                        .size(128.dp),
                    imageModel = R.drawable.ic_launcher_foreground,
                    contentDescription = stringResource(id = R.string.cd_icon_app)
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
                Switch(R.string.default_theme, checkedThemeState.value) { isChecked ->
                    checkedThemeState.value = isChecked
                    viewModel.onUseDefaultThemeChange(isChecked)
                    if (isChecked) {
                        darkThemeState.value = isDark == isChecked
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

    Timber.tag("debug_log")
    Timber.d("SettingsScreen")
}