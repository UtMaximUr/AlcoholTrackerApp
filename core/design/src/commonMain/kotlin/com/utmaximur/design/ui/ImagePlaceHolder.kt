package com.utmaximur.design.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import com.utmaximur.design.extensions.showShimmer
import design.resources.Res
import design.resources.cd_image
import design.resources.ic_image_off_outline
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageLoaderContent(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit
) {
    val context = LocalPlatformContext.current
    SubcomposeAsyncImage(
        modifier = modifier,
        model = remember(context, imageUrl) {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .build()
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = { LoadingImagePlaceHolder() },
        error = { ErrorImagePlaceHolder() },
        success = { state ->
            AnimatedVisibilityContent {
                Image(
                    painter = state.painter,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                )
            }
        }
    )
}


@Composable
private fun AnimatedVisibilityContent(
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    var visibleState by remember { mutableStateOf(false) }
    AnimatedVisibility(
        visible = visibleState,
        enter = fadeIn(
            animationSpec = spring(
                stiffness = Spring.StiffnessMediumLow
            )
        ),
        content = content
    )
    LaunchedEffect(Unit) {
        visibleState = true
    }
}

@Composable
private fun LoadingImagePlaceHolder() = Box(
    modifier = Modifier
        .clip(shape = MaterialTheme.shapes.extraLarge)
        .showShimmer()
        .fillMaxSize()
)

@Composable
private fun ErrorImagePlaceHolder() = Image(
    painter = painterResource(Res.drawable.ic_image_off_outline),
    contentDescription = stringResource(Res.string.cd_image)
)