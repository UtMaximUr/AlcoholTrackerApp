package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel
import com.utmaximur.alcoholtracker.presentation.dialog.delete.DeleteView

@Composable
fun ToolBar(viewModel: CreateTrackViewModel, navController: NavHostController) {

    val openDialog = remember { mutableStateOf(false) }
    val titleState by viewModel.titleFragment.observeAsState()
    val visibleSaveButtonState by viewModel.visibleSaveButtonState.observeAsState(true)
    val visibleEditDrinkButtonState by viewModel.visibleEditDrinkButtonState.observeAsState(false)

    TopAppBar(
        title = {
            Text(text = stringResource(id = titleState ?: R.string.add_drink_title))
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = colorResource(id = R.color.text_color),
        elevation = 2.dp,
        actions = {
            Row {
                AnimatedVisibility(
                    visible = visibleEditDrinkButtonState,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row {
                        IconButton(onClick = { viewModel.onEditClick() }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = {
                            openDialog.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
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
                                contentDescription = null
                            )
                        }
                    }
                    this@Row.AnimatedVisibility(
                        visible = !visibleSaveButtonState,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { viewModel.onCreateClick() }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
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