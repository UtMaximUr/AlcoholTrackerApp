package com.utmaximur.root.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.utmaximur.bottombar.BottomBarState
import com.utmaximur.bottombar.LocalBottomBarController
import com.utmaximur.bottombar.ProvideBottomBarController
import com.utmaximur.message.ui.MessageUi
import com.utmaximur.message.ui.ProvideSnackbarController
import com.utmaximur.root.RootComponent
import com.utmaximur.root.ui.theme.AlcoholTrackerTheme

@Composable
fun RootScreen(
    component: RootComponent
) {
    val state by component.model.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    AlcoholTrackerTheme(state.isDarkTheme) {
        CompositionLocalProvider(
            ProvideBottomBarController(BottomBarState(visible = false)),
            ProvideSnackbarController(snackbarHostState, coroutineScope)
        ) {
            Scaffold(
                bottomBar = {
                    val bottomBarController = LocalBottomBarController.current
                    val bottomBarState by bottomBarController.state
                    AnimatedVisibility(
                        visible = bottomBarState.visible,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = spring(
                                stiffness = Spring.StiffnessHigh
                            )
                        ),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        BottomAppBar(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            content = {
                                BottomBar(
                                    component = component,
                                    isMapEnabled = state.isMapEnabled
                                )
                            }
                        )
                    }
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