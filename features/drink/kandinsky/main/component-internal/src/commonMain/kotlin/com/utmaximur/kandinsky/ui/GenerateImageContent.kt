package com.utmaximur.kandinsky.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.utmaximur.design.extensions.shimmer
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.GenerationStatus
import features.drink.kandinsky.main.Res
import features.drink.kandinsky.main.cd_generate_image
import features.drink.kandinsky.main.error_placeholder_image
import features.drink.kandinsky.main.generation_progress
import features.drink.kandinsky.main.placeholder_image
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GenerateImageContent(
    generationResult: GenerationResult
) {
    ElevatedCardApp(
        modifier = Modifier.aspectRatio(4 / 3f)
    ) {
        when (generationResult.status) {
            GenerationStatus.DONE -> SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .applyBlur(generationResult.censored),
                model = generationResult.imagePathFile,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(Res.string.cd_generate_image),
                error = { ImageHolder(Res.drawable.placeholder_image) }
            )

            GenerationStatus.FAIL -> ImageHolder(
                resource = Res.drawable.error_placeholder_image
            )

            else -> ImageHolder(
                resource = Res.drawable.placeholder_image,
                loading = generationResult.isStatusProgress
            )
        }
    }
}

@Composable
private fun ImageHolder(
    resource: DrawableResource,
    loading: Boolean = false
) = Box(
    contentAlignment = Alignment.Center
) {
    Image(
        modifier = Modifier
            .shimmer(loading)
            .fillMaxSize(),
        painter = painterResource(resource),
        contentDescription = stringResource(Res.string.cd_generate_image)
    )
    AnimatedVisibility(
        visible = loading,
        enter = fadeIn(),
        exit = fadeOut()
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

private fun Modifier.applyBlur(apply: Boolean): Modifier = composed {
    return@composed if (apply) this.blur(
        radiusX = 10.dp,
        radiusY = 10.dp,
        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
    ) else this
}