package com.utmaximur.root.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.active
import com.utmaximur.bottombar.BottomBarState
import com.utmaximur.bottombar.ProvideBottomBarController
import com.utmaximur.message.ui.MessageUi
import com.utmaximur.message.ui.ProvideSnackbarController
import com.utmaximur.root.RootComponent
import com.utmaximur.root.ui.theme.AlcoholTrackerTheme
import com.utmaximur.splash.SplashScreenComponent

@Composable
fun RootScreen(
    component: RootComponent
) {
    val state by component.model.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    AlcoholTrackerTheme(state.isDarkTheme) {
        CompositionLocalProvider(
            ProvideBottomBarController(BottomBarState(visible = component.stack.active.instance !is SplashScreenComponent)),
            ProvideSnackbarController(snackbarHostState, coroutineScope)
        ) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        component = component,
                        isMapEnabled = state.isMapEnabled
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                content = { innerPadding ->
                    MessageUi(component = component.messageComponent)
                    Children(
                        stack = component.stack,
                        animation = stackAnimation(fade()),
                        content = { child ->
                            child.instance.Render(
                                Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                            )
                        }
                    )
                }
            )
        }
    }
}