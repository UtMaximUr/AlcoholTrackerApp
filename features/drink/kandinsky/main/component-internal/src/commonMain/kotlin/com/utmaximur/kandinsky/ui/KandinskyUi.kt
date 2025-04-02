package com.utmaximur.kandinsky.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.utmaximur.design.button.SaveIconButton
import com.utmaximur.design.topbar.TopBar
import com.utmaximur.design.ui.NoInternetConnectionContent
import com.utmaximur.kandinsky.GenerateImageData
import com.utmaximur.kandinsky.KandinskyScreenComponent
import kandinsky.resources.Res
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
                        onRetryStylesLoadingClicked = component::retryStylesLoading,
                        onGenerateImageClicked = component::generateImage
                    )

                    else -> NoInternetConnectionContent()
                }
            }
        },
    )
}