package com.utmaximur.alcoholtracker.presentation.calendar.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.EventDay
import com.utmaximur.alcoholtracker.util.getIdRaw
import timber.log.Timber

@Composable
fun Day(
    day: Int,
    event: EventDay? = null,
    currentDay: Int = -1,
    isCurrentDayOfMonth: Boolean = false,
    onClick: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
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
                    )
                    .clickable(enabled = isCurrentDayOfMonth) { onClick(day) },
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

    Timber.d("event $event")
}