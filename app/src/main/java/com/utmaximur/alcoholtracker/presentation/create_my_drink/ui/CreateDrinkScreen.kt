package com.utmaximur.alcoholtracker.presentation.create_my_drink.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrinkViewModel
import com.utmaximur.alcoholtracker.util.extension.empty
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun CreateDrinkScreen(
    viewModel: CreateMyDrinkViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val photoState = remember { mutableStateOf(String.empty()) }.apply {
        viewModel.onPhotoChange(value)
    }

    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )

    val snackBarHostState = remember { SnackbarHostState() }

    viewModel.emptyFieldState.observeAsState().apply {
        if (!value.isNullOrEmpty()) {
            scope.launch {
                value?.let { text ->
                    snackBarHostState.showSnackbar(
                        message = text
                    )
                }
            }
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        Column {
            TopBar(
                viewModel = viewModel,
                navController = navController
            )
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .padding(12.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 2.dp
            ) {
                GlideImage(
                    imageModel = photoState.value.ifEmpty { R.drawable.ic_camera },
                    contentScale = if (photoState.value.isEmpty()) ContentScale.Inside else ContentScale.Crop,
                    modifier = Modifier.clickable { scope.launch { state.show() } }
                )
            }
            DrinkName(viewModel = viewModel)
            DrinkIcon(viewModel = viewModel)
            DrinkDegree(viewModel = viewModel)
            DrinkVolume(viewModel = viewModel)
        }
        SnackbarHost(hostState = snackBarHostState)
    }

    AddPhotoDialog(
        state = state,
        onResultChange = {
            photoState.value = it
            scope.launch { state.hide() }
        }
    )
}