package com.utmaximur.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.clickableSite
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.settings.SettingsComponent
import features.settings.main.Res
import features.settings.main.currency
import features.settings.main.privacy_policy
import features.settings.main.settings_version
import features.settings.main.sorting_drinks
import features.settings.main.terms_of_use
import features.settings.main.theme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SettingsScreen(
    modifier: Modifier,
    component: SettingsComponent,
) {
    val state by component.model.collectAsState()

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppIconLayout(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.align(Alignment.End),
                text = stringResource(Res.string.settings_version, state.appVersion),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
            ElevatedCardApp(
                modifier = Modifier.fillMaxWidth(),
            ) {
                SettingsButton(
                    title = Res.string.theme,
                    trailingContent = {
                        Switch(
                            modifier = Modifier,
                            checked = state.isDarkTheme,
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                                uncheckedTrackColor = MaterialTheme.colorScheme.background,
                                uncheckedBorderColor = MaterialTheme.colorScheme.outline,
                                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                            ),
                            onCheckedChange = component::changeTheme,
                        )
                    },
                )
                SettingsButton(
                    modifier = Modifier.clickable(onClick = component::openCurrencyDialog),
                    title = Res.string.currency,
                    trailingContent = {
                        Text(
                            text = state.currency,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                )
                SettingsButton(
                    modifier = Modifier.clickable(onClick = component::navigateToSortingDrinks),
                    title = Res.string.sorting_drinks,
                )
                SettingsButton(
                    modifier = Modifier.clickableSite(state.privacyPolicyUrl),
                    title = Res.string.privacy_policy,
                )
                SettingsButton(
                    modifier = Modifier.clickableSite(state.termsOrUseUrl),
                    title = Res.string.terms_of_use,
                )
            }
        }
    }
}
