package com.utmaximur.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.unit.ColorProvider
import com.utmaximur.widget.AddTrackAction
import features.appwidget.component.R

@Composable
internal fun AddTrackButton() {
    val context = LocalContext.current
    Box(
        modifier = GlanceModifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            modifier = GlanceModifier.fillMaxWidth(),
            onClick = actionRunCallback<AddTrackAction>(),
            text = context.getString(R.string.title_add_track),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = GlanceTheme.colors.tertiaryContainer,
                contentColor = ColorProvider(Color.White),
            )
        )
    }
}