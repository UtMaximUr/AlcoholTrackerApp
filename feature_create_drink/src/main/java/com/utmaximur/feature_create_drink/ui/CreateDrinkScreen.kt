package com.utmaximur.feature_create_drink.ui


import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.feature_create_drink.R
import com.utmaximur.feature_create_drink.CreateMyDrinkViewModel
import com.utmaximur.feature_create_drink.state.EmptyFieldState
import com.utmaximur.feature_create_drink.ui.dialog.add_photo.AddPhotoDialog
import com.utmaximur.navigation.NavigationDestination
import com.utmaximur.utils.empty
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CreateDrinkScreen(
    context: Context = LocalContext.current,
    viewModel: CreateMyDrinkViewModel = hiltViewModel(),
    navController: NavHostController,
    editDrinkId: String? = null
) {

    LaunchedEffect(key1 = Unit) {
        editDrinkId?.let {
            viewModel.onDrinkChange(editDrinkId, R.string.edit_drink_title)
        }
    }

    val photoState = remember { mutableStateOf(String.empty()) }.apply {
        viewModel.photoState.value?.let { photo ->
            value = photo
        }
    }

    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )

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

                    if (state.isNotEmpty) {
                        navController.popBackStack(
                            route = NavigationDestination.AddTrackScreen.route,
                            inclusive = false
                        )
                        return@apply
                    }

                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = context.getString(idText)
                        )
                    }
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
                    .height(220.dp)
                    .padding(start = 12.dp, end = 12.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 11.dp
            ) {
                GlideImage(
                    imageModel = photoState.value.ifEmpty { R.drawable.ic_camera },
                    contentScale = if (photoState.value.isEmpty()) ContentScale.Inside else ContentScale.Crop,
                    modifier = Modifier.clickable { scope.launch { state.show() } },
                    contentDescription = stringResource(id = R.string.cd_take_photo)
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
            viewModel.onPhotoChange(it)
            photoState.value = it
            scope.launch { state.hide() }
        }
    )
}