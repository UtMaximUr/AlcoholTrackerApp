package com.utmaximur.widget

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback


internal class AddTrackAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            WidgetDeepLink.ADD_TRACK.deepLink.toUri()
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

internal class OpenAppAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            WidgetDeepLink.OPEN_APP.deepLink.toUri()
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

@Suppress("unused") // action "refresh" for click button on widget
internal class RefreshAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        AppWidget().update(context, glanceId)
    }
}