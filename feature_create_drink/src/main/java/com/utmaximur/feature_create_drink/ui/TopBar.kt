package com.utmaximur.feature_create_drink.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.utmaximur.feature_create_drink.R
import com.utmaximur.feature_create_drink.CreateMyDrinkViewModel

@Composable
fun TopBar(
    viewModel: CreateMyDrinkViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val titleState by viewModel.titleFragment.observeAsState()

    TopAppBar(
        title = {
            Text(text = stringResource(id = titleState ?: R.string.add_new_drink))
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    stringResource(id = R.string.cd_back)
                )
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        actions = {
            IconButton(onClick = {
                viewModel.onSaveButtonClick {
                    navController.popBackStack()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.cd_save_drink)
                )
            }
        }
    )
}