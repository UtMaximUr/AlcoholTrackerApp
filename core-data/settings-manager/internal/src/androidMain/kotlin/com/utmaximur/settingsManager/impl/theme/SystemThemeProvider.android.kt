package com.utmaximur.settingsManager.impl.theme

import android.content.Context
import android.content.res.Configuration
import org.koin.core.annotation.Factory

@Factory
internal class AndroidSystemThemeProvider(
    private val context: Context
) : SystemThemeProvider {

    private val darkTheme: Boolean
        get() = when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> true
            else -> true
        }

    override val isDarkTheme: Boolean = darkTheme
}