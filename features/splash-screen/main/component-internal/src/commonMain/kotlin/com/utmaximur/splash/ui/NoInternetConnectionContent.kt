package com.utmaximur.splash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import splashScreen.resources.Res
import splashScreen.resources.ic_no_network
import splashScreen.resources.internet_connection_title
import splashScreen.resources.cd_internet_connection_logo

@Composable
internal fun NoInternetConnectionContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_no_network),
            contentDescription = stringResource(Res.string.cd_internet_connection_logo),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(Res.string.internet_connection_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}