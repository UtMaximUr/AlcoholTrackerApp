package com.utmaximur.feature_create_drink.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.feature_create_drink.CreateMyDrinkViewModel
import com.utmaximur.feature_create_drink.R
import com.utmaximur.feature_create_drink.ui.dialog.add_photo.AddPhotoDialog
import com.utmaximur.utils.empty
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CreateDrinkScreen(
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
        SnackBar(viewModel = viewModel)
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