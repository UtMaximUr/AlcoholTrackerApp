package com.utmaximur.widget

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.utmaximur.money.store.StatisticMoneyStore
import com.utmaximur.widget.ui.AddTrackButton
import com.utmaximur.widget.ui.HeadlineTitle
import com.utmaximur.widget.ui.StatisticContent
import features.appwidget.component.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AppWidget : GlanceAppWidget(), KoinComponent {

    private val store: StatisticMoneyStore by inject()

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val isSystemDarkMode = remember(key1 = id) {
                val currentNightMode =
                    context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                currentNightMode == Configuration.UI_MODE_NIGHT_YES
            }
            AppWidgetTheme(darkTheme = isSystemDarkMode) {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(GlanceTheme.colors.primaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        modifier = GlanceModifier
                            .padding(vertical = 32.dp),
                        provider = ImageProvider(R.drawable.widget_background),
                        contentDescription = context.getString(R.string.cd_icon),
                    )
                    Column(
                        modifier = GlanceModifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .clickable(actionRunCallback<OpenAppAction>()),
                    ) {
                        HeadlineTitle()
                        StatisticContent(store = store)
                        AddTrackButton()
                    }
                }
            }
        }
    }
}