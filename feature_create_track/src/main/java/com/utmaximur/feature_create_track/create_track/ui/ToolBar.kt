package com.utmaximur.feature_create_track.create_track.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utmaximur.feature_create_track.R
import androidx.navigation.NavHostController
import com.utmaximur.navigation.NavigationDestination
import com.utmaximur.feature_create_track.create_track.CreateTrackViewModel
import com.utmaximur.feature_create_track.create_track.ui.dialog.delete.DeleteView

@Composable
fun ToolBar(viewModel: CreateTrackViewModel, navController: NavHostController) {

    val openDialog = remember { mutableStateOf(false) }
    val titleState by viewModel.titleFragment.observeAsState()
    val visibleSaveButtonState by viewModel.visibleSaveButtonState.observeAsState(true)
    val visibleEditDrinkButtonState by viewModel.visibleEditDrinkButtonState.observeAsState(false)
    val currentDrink by viewModel.drink.observeAsState()

    TopAppBar(
        title = {
            Text(text = stringResource(id = titleState ?: R.string.add_drink_title))
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_back))
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        actions = {
            Row {
                AnimatedVisibility(
                    visible = visibleEditDrinkButtonState,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row {
                        IconButton(onClick = {
                            navController.navigate(
                                NavigationDestination.CreateDrinkScreen.route.plus(
                                    "/${currentDrink?.id}"
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(id = R.string.cd_edit_button)
                            )
                        }
                        IconButton(onClick = {
                            openDialog.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.cd_delete_button)
                            )
                        }
                    }
                }
                Box {
                    this@Row.AnimatedVisibility(
                        visible = visibleSaveButtonState,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            viewModel.onSaveButtonClick()
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = stringResource(id = R.string.cd_save_drink)
                            )
                        }
                    }
                    this@Row.AnimatedVisibility(
                        visible = !visibleSaveButtonState,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            navController.navigate(NavigationDestination.CreateDrinkScreen.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.cd_add_drink)
                            )
                        }
                    }
                }
            }
        }
    )

    if (openDialog.value) {
        DeleteView(
            onNegativeClick = { openDialog.value = false },
            onPositiveClick = {
                viewModel.onDeleteDrink()
                openDialog.value = false
            }
        )
    }
}