package com.utmaximur.alcoholtracker.presentation.settings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.settings.SettingsViewModel
import com.utmaximur.alcoholtracker.util.MARKET_APP
import com.utmaximur.alcoholtracker.util.PRIVACY_POLICY
import com.utmaximur.alcoholtracker.util.TERMS_OF_USE

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val checkedThemeState by viewModel.checkedThemeState.observeAsState()
        val checkedUpdateState by viewModel.checkedUpdateState.observeAsState()

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(elevation = 11.dp) {
                Image(
                    modifier = Modifier.padding(20.dp),
                    painter = painterResource(id = R.drawable.ic_launcher),
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
                color = colorResource(id = R.color.text_color)
            )
        }
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            elevation = 4.dp
        ) {
            Column {
                Switch(R.string.default_theme, checkedThemeState!!) {
                    viewModel.onUseDefaultThemeChange(it)
                }
                ThemeList(checkedThemeState!!, viewModel)
                Switch(R.string.check_for_updates, checkedUpdateState!!) {
                    viewModel.onUpdateChange(it)
                }
                Button(R.string.privacy_policy, PRIVACY_POLICY)
                Button(R.string.terms_of_use, TERMS_OF_USE)
                Button(R.string.rate_app, MARKET_APP + LocalContext.current.packageName)
            }
        }
    }
}