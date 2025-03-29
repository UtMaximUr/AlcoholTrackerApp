package com.utmaximur.kandinsky.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.utmaximur.design.extensions.shimmer
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.GenerationStatus
import kandinsky.resources.Res
import kandinsky.resources.cd_generate_image
import kandinsky.resources.error_placeholder_image
import kandinsky.resources.placeholder_image
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
) = Image(
    modifier = Modifier
        .shimmer(loading)
        .fillMaxSize(),
    painter = painterResource(resource),
    contentDescription = stringResource(Res.string.cd_generate_image)
)

private fun Modifier.applyBlur(apply: Boolean): Modifier = composed {
    return@composed if (apply) this.blur(
        radiusX = 10.dp,
        radiusY = 10.dp,
        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
    ) else this
}