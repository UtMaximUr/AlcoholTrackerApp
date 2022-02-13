package com.utmaximur.alcoholtracker.presentation.main.ui


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.navigation.NavigationDestination

@Composable
fun BottomBar(navController: NavHostController) {

    val bottomNavigationItems = listOf(
        NavigationDestination.CalendarScreen,
        NavigationDestination.StatisticScreen,
        NavigationDestination.SpaceScreen,
        NavigationDestination.SpaceScreen,
        NavigationDestination.SettingsScreen
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.background_color),
        contentColor = colorResource(id = R.color.accent_color),
    ) {
        val currentRoute = currentRoute(navController)
        bottomNavigationItems.forEach { screen ->
            if (screen.route.isNotEmpty()) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = screen.resourceId)) },
                    selected = currentRoute == screen.route,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            } else {
                Spacer(modifier = Modifier.fillMaxWidth(0.2f))
            }
        }
    }
}