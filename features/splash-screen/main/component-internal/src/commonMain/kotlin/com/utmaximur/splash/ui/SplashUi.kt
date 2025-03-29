package com.utmaximur.splash.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.ui.NoInternetConnectionContent
import com.utmaximur.splash.SplashScreenComponent

@Composable
internal fun SplashScreen(
    component: SplashScreenComponent
) {
    val state by component.model.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        ContentHolder(innerPadding) {
            RequestWidget(
                state = state.requestUi
            ) { internetAvailable ->
                when {
                    internetAvailable -> SplashContent(component::readyToLoad)
                    else -> NoInternetConnectionContent()
                }
            }
        }
    }
}
