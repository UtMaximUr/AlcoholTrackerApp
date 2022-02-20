package com.utmaximur.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


sealed class NavigationDestination(
    val route: String = "",
    @StringRes val resourceId: Int = 0,
    @DrawableRes val icon: Int = 0
) {
    object CalendarScreen :
        NavigationDestination("calendar_screen", R.string.calendar, R.drawable.ic_calendar_24dp)

    object StatisticScreen :
        NavigationDestination("statistic_screen", R.string.statistic, R.drawable.ic_statistic_24dp)

    object SettingsScreen :
        NavigationDestination("settings_screen", R.string.settings, R.drawable.ic_settings_24dp)

    object AddTrackScreen :
        NavigationDestination(route = "add_track_screen")

    object SpaceScreen :
        NavigationDestination()

    object CreateDrinkScreen :
        NavigationDestination(route = "create_drink_screen")

}

val bottomNavigationItems = listOf(
    NavigationDestination.CalendarScreen,
    NavigationDestination.StatisticScreen,
    NavigationDestination.SpaceScreen,
    NavigationDestination.SpaceScreen,
    NavigationDestination.SettingsScreen
)