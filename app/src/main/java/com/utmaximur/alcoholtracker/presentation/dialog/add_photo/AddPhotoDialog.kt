package com.utmaximur.alcoholtracker.presentation.dialog.add_photo

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.util.extension.empty

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun AddPhotoDialog(
    viewModel: AddPhotoViewModel = hiltViewModel(),
    state: ModalBottomSheetState,
    onResultChange: (String) -> Unit
) {

    val photoState = remember { mutableStateOf(String.empty()) }
    val permissionState = remember { mutableStateOf(String.empty()) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            photoState.value = viewModel.saveImage(it).toString()
            onResultChange(photoState.value)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        photoState.value = viewModel.saveImage().toString()
        onResultChange(photoState.value)
    }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetElevation = 12.dp,
        sheetContent = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.bottom_sheet_option_heading),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp),
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 16.sp
                )
                ButtonDialog(
                    icon = R.drawable.ic_add_photo,
                    text = R.string.bottom_sheet_option_camera,
                    onClick = { permissionState.value = CAMERA }
                )
                ButtonDialog(
                    icon = R.drawable.ic_gallery,
                    text = R.string.bottom_sheet_option_gallery,
                    onClick = { permissionState.value = READ_EXTERNAL_STORAGE }
                )
                Divider(
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp)
                        .width(1.dp)
                )
                ButtonDialog(
                    icon = R.drawable.ic_delete,
                    text = R.string.bottom_sheet_option_remove_photo,
                    onClick = {
                        viewModel.deleteImage()
                        photoState.value = String.empty()
                        onResultChange(photoState.value)
                    }
                )
            }
        }
    ) { }

    when (permissionState.value) {
        READ_EXTERNAL_STORAGE -> {
            PermissionsRequest(
                permissions = READ_EXTERNAL_STORAGE,
                onGranted = {
                    galleryLauncher.launch("image/*")
                    permissionState.value = String.empty()
                }
            )
        }
        CAMERA -> {
            PermissionsRequest(
                permissions = CAMERA,
                onGranted = {
                    cameraLauncher.launch(viewModel.createImageUri())
                    permissionState.value = String.empty()
                }
            )
        }
    }
}

@Composable
fun ButtonDialog(
    icon: Int,
    text: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(id = text)
        )
        Spacer(modifier = Modifier.padding(start = 32.dp))
        Text(
            text = stringResource(id = text),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRequest(
    permissions: String,
    onGranted: () -> Unit,
    onDenied: (@Composable () -> Unit)? = null
) {

    val permissionState = rememberPermissionState(permissions)

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted)
            permissionState.launchPermissionRequest()
    }

    when (permissionState.status) {
        PermissionStatus.Granted -> onGranted.invoke()
        is PermissionStatus.Denied -> onDenied?.invoke()
    }
}