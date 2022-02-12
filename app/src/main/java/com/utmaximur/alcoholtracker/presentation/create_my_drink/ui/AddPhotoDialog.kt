package com.utmaximur.alcoholtracker.presentation.create_my_drink.ui

import android.Manifest.permission.*
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.utmaximur.alcoholtracker.presentation.dialog.add_photo.AddPhotoViewModel
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.toBitmap

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun AddPhotoDialog(
    viewModel: AddPhotoViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    state: ModalBottomSheetState,
    onResultChange: (String) -> Unit
) {

    val photoState = remember { mutableStateOf(String.empty()) }
    val permissionState = remember { mutableStateOf(String.empty()) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            photoState.value = viewModel.savePhoto(it.toBitmap(context))
            onResultChange(photoState.value)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            photoState.value = viewModel.savePhoto(it)
            onResultChange(photoState.value)
        }
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
                    color = colorResource(id = R.color.text_color),
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
                    cameraLauncher.launch()
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = colorResource(id = R.color.text_color),
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(start = 32.dp))
        Text(
            text = stringResource(id = text),
            modifier = Modifier.weight(1f),
            color = colorResource(id = R.color.text_color),
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