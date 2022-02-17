package com.utmaximur.alcoholtracker.presentation.main.ui


import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.update.UpdateManager
import com.utmaximur.alcoholtracker.navigation.NavigationDestination.*
import com.utmaximur.alcoholtracker.presentation.calendar.ui.CalendarScreen
import com.utmaximur.alcoholtracker.presentation.create_my_drink.ui.CreateDrinkScreen
import com.utmaximur.alcoholtracker.presentation.create_track.ui.CreateTrackerScreen
import com.utmaximur.alcoholtracker.presentation.dialog.track_list.ui.TrackListBottomDialogScreen
import com.utmaximur.alcoholtracker.presentation.dialog.update.UpdateDialog
import com.utmaximur.alcoholtracker.presentation.settings.ui.SettingsScreen
import com.utmaximur.alcoholtracker.presentation.main.ui.theme.AlcoholTrackerTheme
import com.utmaximur.alcoholtracker.presentation.main.ui.theme.TextColorWhite
import com.utmaximur.alcoholtracker.presentation.statistic.ui.StatisticScreen
import com.utmaximur.alcoholtracker.util.*
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {

    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()

    UpdateApp()

    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val darkThemeState =
        rememberSaveable { (mutableStateOf(themeApp(context, isDark))) }
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            TrackListBottomDialogScreen(
                navController = navController,
                modalBottomSheetState = modalBottomSheetState
            )
        },
        sheetState = modalBottomSheetState
    ) {
        AlcoholTrackerTheme(darkTheme = darkThemeState.value) {
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
                    innerPadding = innerPadding
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
    innerPadding: PaddingValues
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
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
private fun UpdateApp() {
    Timber.d("UpdateApp")

    val context = LocalContext.current

    val openDialog = remember { mutableStateOf(false) }

    val sharedPrefs by lazy { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    val savedUpdate = sharedPrefs.getBoolean(KEY_UPDATE, true)

    Timber.d("UpdateApp/savedUpdate = $savedUpdate")

    if (savedUpdate) {
        UpdateManager.getInstance().registerListener()
        UpdateManager.getInstance().attachUpdateListener(object : UpdateManager.UpdateListener {
            override fun onShowUpdateDialog() {
                openDialog.value = true
            }
        })
        UpdateManager.getInstance().checkForUpdate(context)
    }

    if (openDialog.value) {
        UpdateDialog(
            onLaterClick = { openDialog.value = false },
            onNowClick = { UpdateManager.getInstance().completeUpdate() }
        )
    }
}

private fun themeApp(context: Context, isDark: Boolean): Boolean {
    val sharedPrefs by lazy { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    return when (sharedPrefs?.getInt(KEY_THEME, THEME_UNDEFINED)) {
        THEME_DARK -> true
        THEME_LIGHT -> false
        else -> isDark
    }
}