package com.utmaximur.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver


internal class AppWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = AppWidget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        // TODO init workManager
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        // TODO cancel workManager
    }
}