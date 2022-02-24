package com.utmaximur.feature_create_drink.ui

import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.utmaximur.feature_create_drink.CreateMyDrinkViewModel
import com.utmaximur.feature_create_drink.R
import com.utmaximur.feature_create_drink.state.EmptyFieldState
import kotlinx.coroutines.launch

@Composable
fun SnackBar(viewModel: CreateMyDrinkViewModel) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    viewModel.emptyFieldState.observeAsState().apply {
        value?.let { state ->
            when (state) {
                is EmptyFieldState.Empty -> {
                    var idText = 0
                    if (state.isPhotoEmpty)
                        idText = R.string.empty_field_photo

                    if (state.isNameEmpty)
                        idText = R.string.empty_field_name

                    if (state.isIconEmpty)
                        idText = R.string.empty_field_icon

                    if (state.isDegreeEmpty)
                        idText = R.string.empty_field_degree

                    if (state.isVolumeEmpty)
                        idText = R.string.empty_field_volume

                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = context.getString(idText)
                        )
                    }
                }
            }
        }
    }

    SnackbarHost(hostState = snackBarHostState)
}