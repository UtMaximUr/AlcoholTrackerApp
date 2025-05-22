package com.utmaximur.root.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
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
import com.utmaximur.bottombar.LocalBottomBarController
import com.utmaximur.calendar.CalendarNavigationComponent
import com.utmaximur.map.MapNavigationComponent
import com.utmaximur.root.RootComponent
import com.utmaximur.settings.SettingsNavigationComponent
import com.utmaximur.statistic.StatisticComponent
import features.root.Res
import features.root.calendar
import features.root.ic_calendar_24dp
import features.root.ic_map_24dp
import features.root.ic_settings_24dp
import features.root.ic_statistic_24dp
import features.root.map
import features.root.settings
import features.root.statistic
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BottomBar(
    component: RootComponent,
    isMapEnabled: Boolean
) {
    val stack by component.stack.subscribeAsState()
    val activeChild = stack.active.instance
    val bottomBarController = LocalBottomBarController.current
    val bottomBarState by bottomBarController.state

    AnimatedVisibility(
        visible = bottomBarState.visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(stiffness = Spring.StiffnessHigh)
        ),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentPadding = PaddingValues(horizontal = 0.dp),
            content = {
                NavigationBar(containerColor = Color.Transparent) {
                    NavigationItem(
                        icon = Res.drawable.ic_calendar_24dp,
                        title = Res.string.calendar,
                        selected = activeChild is CalendarNavigationComponent,
                        onClick = component::onCalendarScreenClicked
                    )
                    NavigationItem(
                        icon = Res.drawable.ic_map_24dp,
                        title = Res.string.map,
                        selected = activeChild is MapNavigationComponent,
                        enabled = isMapEnabled,
                        onClick = component::onMapScreenClicked
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
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.NavigationItem(
    icon: DrawableResource,
    title: StringResource,
    iconTint: Color? = null,
    selected: Boolean = false,
    enabled: Boolean = true,
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
            enabled = enabled,
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