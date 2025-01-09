package com.utmaximur.settingsManager.impl.theme

import org.koin.core.annotation.Factory
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.currentTraitCollection

@Factory
internal class IosSystemThemeProvider : SystemThemeProvider {
    override val isDarkTheme: Boolean
        get() = when (UITraitCollection.currentTraitCollection.userInterfaceStyle) {
            UIUserInterfaceStyle.UIUserInterfaceStyleLight -> false
            UIUserInterfaceStyle.UIUserInterfaceStyleDark -> true
            UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified -> true
            else -> true
        }

}