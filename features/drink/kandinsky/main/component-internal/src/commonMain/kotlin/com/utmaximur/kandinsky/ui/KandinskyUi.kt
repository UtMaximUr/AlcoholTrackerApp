package com.utmaximur.kandinsky.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.button.LoadingButton
import com.utmaximur.design.button.SaveIconButton
import com.utmaximur.design.topbar.TopBar
import com.utmaximur.design.ui.NoInternetConnectionContent
import com.utmaximur.kandinsky.GenerateImageData
import com.utmaximur.kandinsky.KandinskyScreenComponent
import kandinsky.resources.Res
import kandinsky.resources.create_generation
import kandinsky.resources.title_generate_image
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun KandinskyScreen(
    component: KandinskyScreenComponent,
    requestBuilder: GenerateImageData.Builder
) {
    val state by component.model.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = component::navigateBack,
                title = stringResource(Res.string.title_generate_image),
                actions = {
                    SaveIconButton(
                        enabled = state.isDoneStatus,
                        onClick = component::applyGeneratedImage
                    )
                }
            )
        },
        content = { innerPadding ->
            Crossfade(
                targetState = state.internetAvailable
            ) { internetAvailable ->
                when {
                    internetAvailable -> Content(
                        modifier = Modifier.padding(innerPadding),
                        requestBuilder = requestBuilder,
                        requestStylesUi = state.requestStylesUi,
                        isProgressStatus = state.isProgressStatus,
                        generationResult = state.generationResult,
                        onRetryStylesLoadingClicked = component::retryStylesLoading
                    )

                    else -> NoInternetConnectionContent()
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = state.internetAvailable,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LoadingButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .navigationBarsPadding()
                        .fillMaxWidth(),
                    text = stringResource(Res.string.create_generation),
                    onClick = component::generateImage,
                    loading = state.isProgressStatus,
                    enabled = !state.isProgressStatus
                )
            }
        },
    )
}