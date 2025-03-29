package com.utmaximur.design.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.bounceClick

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    loading: Boolean,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.tertiary
) {
    val transition = updateTransition(
        targetState = loading,
        label = "transition"
    )
    TextButton(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.textButtonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor
        ),
        modifier = modifier.bounceClick()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            LoadingContent(
                loadingStateTransition = transition
            )
            PrimaryContent(
                text = text,
                loadingStateTransition = transition
            )
        }
    }
}

@Composable
private fun LoadingContent(
    loadingStateTransition: Transition<Boolean>
) {
    loadingStateTransition.AnimatedVisibility(
        visible = { loading -> loading },
        enter = fadeIn(),
        exit = fadeOut(
            animationSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                visibilityThreshold = 0.10f
            )
        )
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(22.dp),
            color = Color.White,
            strokeWidth = 1.5f.dp,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun PrimaryContent(
    text: String,
    loadingStateTransition: Transition<Boolean>
) {
    loadingStateTransition.AnimatedVisibility(
        visible = { loading -> !loading },
        enter = fadeIn() + expandHorizontally(
            animationSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                dampingRatio = Spring.DampingRatioMediumBouncy,
                visibilityThreshold = IntSize.VisibilityThreshold
            ),
            expandFrom = Alignment.CenterHorizontally
        ),
        exit = fadeOut(
            animationSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                visibilityThreshold = 0.10f
            )
        ) + shrinkHorizontally(
            animationSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                visibilityThreshold = IntSize.VisibilityThreshold
            ),
            shrinkTowards = Alignment.CenterHorizontally
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}