package com.utmaximur.alcoholtracker.presentation.main.ui


import androidx.compose.animation.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.main.ui.theme.AlcoholTrackerTheme
import com.utmaximur.alcoholtracker.presentation.main.ui.theme.TextColorWhite
import com.utmaximur.feature_calendar.calendar.ui.CalendarScreen
import com.utmaximur.feature_calendar.track_list.ui.TrackListBottomDialogScreen
import com.utmaximur.feature_create_drink.ui.CreateDrinkScreen
import com.utmaximur.feature_create_track.create_track.ui.CreateTrackerScreen
import com.utmaximur.feature_settings.settings.ui.SettingsScreen
import com.utmaximur.feature_statistic.statistic.ui.StatisticScreen
import com.utmaximur.feature_update.UpdateDialog
import com.utmaximur.navigation.BottomBar
import com.utmaximur.navigation.NavigationDestination.*
import com.utmaximur.utils.EDIT_DRINK
import com.utmaximur.utils.EDIT_TRACK


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    themeApp: Boolean?,
    navController: NavHostController = rememberNavController(),
    isDark: Boolean = isSystemInDarkTheme()
) {

    val bottomBarState = rememberSaveable {
        (mutableStateOf(true))
    }
    val darkThemeState = rememberSaveable {
        (mutableStateOf(themeApp ?: isDark))
    }
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    AlcoholTrackerTheme(darkTheme = darkThemeState.value) {
        SystemUiColor(darkThemeState.value)
        ModalBottomSheetLayout(
            sheetContent = {
                TrackListBottomDialogScreen(
                    navController = navController,
                    modalBottomSheetState = modalBottomSheetState
                )
            },
            sheetState = modalBottomSheetState,
            sheetBackgroundColor = MaterialTheme.colors.background
        ) {
            Scaffold(
                bottomBar = {
                    AnimatedVisibility(
                        visible = bottomBarState.value,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        BottomAppBar(
                            cutoutShape = CircleShape,
                            backgroundColor = MaterialTheme.colors.background,
                        ) {
                            BottomBar(navController = navController)
                        }
                    }
                },
                snackbarHost = {
                    UpdateDialog()
                },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
                floatingActionButton = {
                    AnimatedVisibility(
                        visible = bottomBarState.value,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        AddTrackButton(navController = navController)
                    }
                }
            ) { innerPadding ->
                BottomNavScreensController(
                    navController = navController,
                    bottomBarState = bottomBarState,
                    darkThemeState = darkThemeState,
                    modalBottomSheetState = modalBottomSheetState,
                    innerPadding = innerPadding.calculateBottomPadding() + 24.dp
                )
            }
        }
    }
}

@Composable
fun AddTrackButton(navController: NavHostController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(AddTrackScreen.route) {
                launchSingleTop = true
                restoreState = true
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_fab),
                contentDescription = null,
                tint = TextColorWhite
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavScreensController(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    darkThemeState: MutableState<Boolean>,
    modalBottomSheetState: ModalBottomSheetState,
    innerPadding: Dp
) {
    NavHost(navController, startDestination = CalendarScreen.route) {
        composable(CalendarScreen.route) {
            bottomBarState.value = true
            CalendarScreen(
                navController = navController,
                innerPadding = innerPadding,
                modalBottomSheetState = modalBottomSheetState
            )
        }
        composable(AddTrackScreen.route) {
            bottomBarState.value = false
            CreateTrackerScreen(navController = navController)
        }
        composable(AddTrackScreen.route.plus("/{$EDIT_TRACK}")) {
            bottomBarState.value = false
            CreateTrackerScreen(
                navController = navController,
                editTrackId = it.arguments?.getString(EDIT_TRACK)
            )
        }
        composable(StatisticScreen.route) {
            bottomBarState.value = true
            StatisticScreen(innerPadding = innerPadding)
        }
        composable(SettingsScreen.route) {
            bottomBarState.value = true
            SettingsScreen(innerPadding = innerPadding, darkThemeState = darkThemeState)
        }
        composable(CreateDrinkScreen.route) {
            bottomBarState.value = false
            CreateDrinkScreen(navController = navController)
        }
        composable(CreateDrinkScreen.route.plus("/{$EDIT_DRINK}")) {
            bottomBarState.value = false
            CreateDrinkScreen(
                navController = navController,
                editDrinkId = it.arguments?.getString(EDIT_DRINK)
            )
        }
    }
}

@Composable
private fun SystemUiColor(darkThemeState: Boolean) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.background,
        darkIcons = !darkThemeState
    )
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.background,
        darkIcons = !darkThemeState
    )
}