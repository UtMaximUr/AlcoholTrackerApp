package com.utmaximur.kandinsky.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.ImageStyle
import com.utmaximur.kandinsky.GenerateImageData
import kandinsky.resources.Res
import kandinsky.resources.generation_progress
import kandinsky.resources.title_enter_text
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    requestBuilder: GenerateImageData.Builder,
    requestStylesUi: RequestUi<List<ImageStyle>>,
    isProgressStatus: Boolean,
    generationResult: GenerationResult,
    onRetryStylesLoadingClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AnimatedVisibility(
            visible = isProgressStatus,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            ElevatedCardApp(
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = stringResource(Res.string.generation_progress),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
        GenerateImageContent(
            generationResult = generationResult
        )
        ElevatedCardApp(
            contentPaddingValues = PaddingValues(12.dp),
        ) {
            InnerShadowTextField(
                title = stringResource(Res.string.title_enter_text),
                onValueChange = requestBuilder::setPrompt,
            )
        }
        ElevatedCardApp(
            contentPaddingValues = PaddingValues(12.dp)
        ) {
            RequestWidget(
                state = requestStylesUi,
                shimmerContentTemplate = { ImageStyleSelectShimmer() },
                onRetryClick = onRetryStylesLoadingClicked,
                content = { imageStyles ->
                    ImageStyleSelectContent(
                        imageStyles = imageStyles,
                        onItemSelected = requestBuilder::setStyle
                    )
                }
            )
        }
    }
}