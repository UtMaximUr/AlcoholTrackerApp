package com.utmaximur.feature_calendar.calendar.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.feature_calendar.R
import com.utmaximur.utils.getIdRaw
import com.utmaximur.domain.entity.EventDay

@Composable
fun Day(
    day: Int,
    event: EventDay? = null,
    currentDay: Int = -1,
    isCurrentDayOfMonth: Boolean = false,
    onClick: (Int) -> Unit = {},
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = isCurrentDayOfMonth
            ) { onClick(day) }
            .alpha(if (isCurrentDayOfMonth) 1f else 0.2f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(50.dp),
            elevation = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (day == currentDay)
                            MaterialTheme.colors.primary
                        else MaterialTheme.colors.background
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = day.toString(),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                    color = if (day == currentDay) Color.White else MaterialTheme.colors.onPrimary
                )
            }
        }

        AnimatedVisibility(visible = event != null, enter = fadeIn()) {
            GlideImage(
                imageModel = event?.idDrawable?.getIdRaw(LocalContext.current),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(24.dp)
                    .aspectRatio(1f)
                    .padding(2.dp),
                contentDescription = null
            )
        }
    }
}