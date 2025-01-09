package com.utmaximur.design.extensions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import kotlin.math.absoluteValue

@OptIn(ExperimentalStdlibApi::class)
fun String.parseColor(): Color {
    return Color(
        this
            .removePrefix("#")
            .hexToLong(HexFormat.Default)
    ).copy(alpha = 1f)
}

fun Modifier.shimmer(showShimmer: Boolean): Modifier = composed {
    val shimmerInstance = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = defaultShimmerTheme.copy(
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1_000,
                    delayMillis = 300,
                    easing = LinearEasing
                )
            ),
            blendMode = BlendMode.Src,
            shaderColors = listOf(
                Color.Unspecified.copy(alpha = 0.01f),
                Color(0xFFE0E2E9),
                Color.Unspecified.copy(alpha = 0.01f)
            ),
            shaderColorStops = null
        )
    )
    return@composed if (showShimmer) this.shimmer(shimmerInstance) else this
}

fun Modifier.backgroundItem(isFirst: Boolean, isLast: Boolean) = composed {
    this.then(
        Modifier.background(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = when {
                isFirst && isLast -> MaterialTheme.shapes.extraLarge
                isFirst -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                isLast -> RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                else -> RectangleShape
            }
        )
    )
}

fun String?.clearTags() = this?.replace("<[^>]*>".toRegex(), "").orEmpty()


/**
 * Добавляет эфффект размытия.
 */
val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.015f to Color.Red)
val bottomFade = Brush.verticalGradient(0.985f to Color.Red, 1f to Color.Transparent)
val startFade = Brush.horizontalGradient(0f to Color.Transparent, 0.055f to Color.Red)
val endFade = Brush.horizontalGradient(0.945f to Color.Red, 1f to Color.Transparent)
fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

fun Modifier.drawBehind(color: Color) = this
    .drawBehind {
        val strokeWidthPx = 1.dp.toPx()
        val verticalOffset = size.height - 2.sp.toPx()
        drawLine(
            color = color,
            strokeWidth = strokeWidthPx,
            start = Offset(0f, verticalOffset),
            end = Offset(size.width, verticalOffset)
        )
    }

/**
 * Добавляет возможность закрыть полноэкранный диалог свайпом вверх или вниз.
 */
fun Modifier.swipeable(onDismissRequest: () -> Unit): Modifier = composed {
    var offset by remember { mutableFloatStateOf(0f) }
    val alpha: Float by animateFloatAsState(1 - (offset.absoluteValue / 300))
    if (alpha <= 0) {
        onDismissRequest()
        return@composed this.alpha(0f)
    }
    return@composed this
        .background(Color.Black.copy(alpha = alpha))
        .offset(y = offset.dp)
        .alpha(alpha)
        .pointerInput(Unit) {
            detectVerticalDragGestures(
                onDragStart = { offset = 0f },
                onDragEnd = {
                    if (offset < -250 || offset > 250) {
                        onDismissRequest()
                    } else {
                        offset = 0f
                    }
                }
            ) { _, dragAmount ->
                offset += dragAmount
            }
        }
}

/**
 * Обрабатывает намерение открыть сайт, телефон, почту
 */
@Composable
fun Modifier.clickablePhone(phone: String): Modifier {
    val uriHandler = LocalUriHandler.current
    return this.clickable { uriHandler.openUri("tel://$phone") }
}

@Composable
fun Modifier.clickableSite(site: String, clickableHandler: () -> Unit = {}): Modifier {
    val uriHandler = LocalUriHandler.current
    val formattedSite = when {
        site.contains("https") -> site
        site.contains("http") -> site
        else -> "https://$site"
    }
    return this.clickable {
        clickableHandler()
        uriHandler.openUri(formattedSite)
    }
}

@Composable
fun Modifier.clickableMail(mail: String): Modifier {
    val uriHandler = LocalUriHandler.current
    return this.clickable { uriHandler.openUri("mailto://$mail") }
}

/**
 * Добавляет анимацию изменеия Alignment.
 */
fun Modifier.animatePlacement(): Modifier = composed {
    val scope = rememberCoroutineScope()
    var targetOffset by remember { mutableStateOf(IntOffset.Zero) }
    var animatable by remember {
        mutableStateOf<Animatable<IntOffset, AnimationVector2D>?>(null)
    }
    this
        .onPlaced {
            targetOffset = it
                .positionInParent()
                .round()
        }
        .offset {
            val anim = animatable ?: Animatable(targetOffset, IntOffset.VectorConverter)
                .also {
                    animatable = it
                }

            if (anim.targetValue != targetOffset) {
                scope.launch {
                    anim.animateTo(targetOffset, spring(stiffness = Spring.StiffnessMedium))
                }
            }
            animatable?.let { it.value - targetOffset } ?: IntOffset.Zero
        }
}

/**
 * Добавляет возможность скрыть клавиатуру свайпом вверх/вниз ил тапом.
 */
fun Modifier.swipeToCloseKeyboard(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    this.pointerInput(Unit) {
        detectVerticalDragGestures(
            onDragStart = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ) { _, _ ->
        }
    }
}

fun Modifier.tapToCloseKeyboard(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    this.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    }
}

/**
 * Добавляет эффект внутренней тени.
 *
 * @param color - Цвет внутренней тени.
 * @param cornersRadius - Радиус закругления углов. Определяет, насколько закруглены углы прямоугольной области с тенью.
 * @param blur - Степень размытия тени. Чем больше значение, тем больше размытость.
 */
@Composable
fun Modifier.innerShadow(
    color: Color = MaterialTheme.colorScheme.surfaceTint,
    cornersRadius: Dp = 8.dp,
    blur: Dp = 10.dp,
): Modifier = drawWithContent {
    drawContent()
    val rect = Rect(Offset.Zero, size)
    val paint = Paint()

    drawIntoCanvas {
        paint.color = color
        paint.isAntiAlias = true
        it.saveLayer(rect, paint)
        it.drawRoundRect(
            left = rect.left,
            top = rect.top,
            right = rect.right,
            bottom = rect.bottom,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
        paint.applyBlurNativePaint(blur.toPx())
        paint.color = Color.Black
        it.drawRoundRect(
            left = rect.left,
            top = rect.top,
            right = rect.right,
            bottom = rect.bottom,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

expect fun Paint.applyBlurNativePaint(blurRadius: Float)

/**
 * Добавляет эффект нажатия.
 *
 */
enum class ButtonState { Pressed, Idle }
fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.95f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}