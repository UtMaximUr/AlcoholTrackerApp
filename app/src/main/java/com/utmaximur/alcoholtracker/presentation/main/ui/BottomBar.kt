package com.utmaximur.alcoholtracker.presentation.main.ui


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        elevation = 0.dp
    ) {
        val currentRoute = currentRoute(navController)
        bottomNavigationItems.forEach { screen ->
            if (screen.route.isNotEmpty()) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = stringResource(id = screen.resourceId)
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