package com.utmaximur.alcoholtracker.presentation.main.ui


import android.content.Context
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.utmaximur.alcoholtracker.presentation.dialog.update.UpdateDialog
import com.utmaximur.alcoholtracker.presentation.settings.ui.SettingsScreen
import com.utmaximur.alcoholtracker.presentation.splash.ui.theme.AlcoholTrackerTheme
import com.utmaximur.alcoholtracker.presentation.statistic.ui.StatisticScreen
import com.utmaximur.alcoholtracker.util.DRINK
import com.utmaximur.alcoholtracker.util.KEY_UPDATE
import com.utmaximur.alcoholtracker.util.PREFS_NAME

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    bottomBarState.value =
        currentRoute(navController) != AddTrackScreen.route
                && currentRoute(navController)?.contains(CreateDrinkScreen.route) == false

    UpdateApp()

    AlcoholTrackerTheme {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState.value,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    BottomAppBar(
                        cutoutShape = CircleShape,
                        backgroundColor = Color.White,
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
            BottomNavScreensController(navController = navController, innerPadding = innerPadding)
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
        backgroundColor = colorResource(id = R.color.accent_color),
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_fab),
                contentDescription = null,
                tint = colorResource(id = R.color.white)
            )
        }
    )
}

@Composable
fun BottomNavScreensController(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController, startDestination = CalendarScreen.route) {
        composable(CalendarScreen.route) {
            CalendarScreen(innerPadding = innerPadding)
        }
        composable(AddTrackScreen.route) {
            CreateTrackerScreen(navController = navController)
        }
        composable(StatisticScreen.route) {
            StatisticScreen(innerPadding = innerPadding)
        }
        composable(SettingsScreen.route) {
            SettingsScreen(innerPadding = innerPadding)
        }
        composable(CreateDrinkScreen.route.plus("/{$DRINK}")) {
            CreateDrinkScreen(
                navController = navController,
                editDrinkId = it.arguments?.getString(DRINK)
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
    Log.d("debug_log", "UpdateApp")

    val context = LocalContext.current

    val openDialog = remember { mutableStateOf(false) }

    val sharedPrefs by lazy { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    val savedUpdate = sharedPrefs.getBoolean(KEY_UPDATE, true)

    Log.d("debug_log", "savedUpdate = $savedUpdate")

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