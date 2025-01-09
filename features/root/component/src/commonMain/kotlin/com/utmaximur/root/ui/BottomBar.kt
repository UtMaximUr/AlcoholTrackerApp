package com.utmaximur.root.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.utmaximur.calendar.CalendarNavigationComponent
import com.utmaximur.root.RootComponent
import com.utmaximur.settings.SettingsNavigationComponent
import com.utmaximur.statistic.StatisticComponent
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import root.resources.Res
import root.resources.calendar
import root.resources.ic_calendar_24dp
import root.resources.ic_settings_24dp
import root.resources.ic_statistic_24dp
import root.resources.settings
import root.resources.statistic

@Composable
internal fun BottomBar(
    component: RootComponent
) {

    val stack by component.stack.subscribeAsState()
    val activeChild = stack.active.instance

    NavigationBar(containerColor = Color.Transparent) {
        NavigationItem(
            icon = Res.drawable.ic_calendar_24dp,
            title = Res.string.calendar,
            selected = activeChild is CalendarNavigationComponent,
            onClick = component::onCalendarScreenClicked
        )
        NavigationItem(
            icon = Res.drawable.ic_statistic_24dp,
            title = Res.string.statistic,
            selected = activeChild is StatisticComponent,
            onClick = component::onStatisticScreenClicked
        )
        NavigationItem(
            icon = Res.drawable.ic_settings_24dp,
            title = Res.string.settings,
            selected = activeChild is SettingsNavigationComponent,
            onClick = component::onSettingsScreenClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.NavigationItem(
    icon: DrawableResource,
    title: StringResource,
    iconTint: Color? = null,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        NavigationBarItem(
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(icon),
                    contentDescription = stringResource(title),
                    tint = iconTint ?: LocalContentColor.current
                )
            },
            label = {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            selected = selected,
            onClick = onClick,
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = MaterialTheme.colorScheme.primary,
                selectedIconColor = MaterialTheme.colorScheme.tertiary,
                selectedTextColor = MaterialTheme.colorScheme.tertiary,
                indicatorColor = Color.Transparent
            )
        )
    }
}