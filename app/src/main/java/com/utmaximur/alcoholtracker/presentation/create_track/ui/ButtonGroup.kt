package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utmaximur.alcoholtracker.R

@ExperimentalAnimationApi
@Composable
fun ButtonGroup(
    dateState: String,
    visibleTodayState: Boolean,
    onSelectDateClick: () -> Unit,
    onTodayClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Button(
                text = dateState,
                onSelectDateClick = onSelectDateClick
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        AnimatedVisibility(
            visible = visibleTodayState,
            enter = expandVertically()
        ) {
            OutlineButton(
                text = R.string.add_today,
                onTodayClick = onTodayClick
            )
        }
    }
}

@Composable
fun Button(
    text: String,
    size: TextUnit = 16.sp,
    radius: Int = 50,
    onSelectDateClick: () -> Unit
) {
   Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(radius),
        onClick = { onSelectDateClick() }
    ) {
        Text(
            text = text,
            fontSize = size,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color_white)
        )
    }
}

@Composable
fun OutlineButton(
    text: Int,
    size: TextUnit = 16.sp,
    radius: Int = 50,
    onTodayClick: () -> Unit) {
    OutlinedButton(
        shape = RoundedCornerShape(radius),
        onClick = { onTodayClick() }
    ) {
        Text(
            text = stringResource(id = text),
            fontSize = size,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
    }
}