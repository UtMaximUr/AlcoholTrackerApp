package com.utmaximur.actions.ui

import actions.resources.Res
import actions.resources.action_camera
import actions.resources.action_gallery
import actions.resources.action_generate_image
import actions.resources.action_heading
import actions.resources.action_remove_photo
import actions.resources.ic_camera
import actions.resources.ic_delete
import actions.resources.ic_gallery
import actions.resources.ic_generate_image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.actions.ActionsImageComponent
import com.utmaximur.design.modal.ModalBottomSheetApp
import com.utmaximur.media.FilePickerFileType
import com.utmaximur.media.FilePickerSelectionMode
import com.utmaximur.media.rememberCameraManager
import com.utmaximur.media.rememberFilePickerLauncher
import com.utmaximur.permission.PermissionType
import com.utmaximur.permission.state.rememberPermissionState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ActionsSelectBottomSheet(
    component: ActionsImageComponent,
) {
    val cameraManager = rememberCameraManager(
        onResult = component::handleFile,
    )
    val filePicker = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = component::handleFiles,
    )
    val cameraPermissionState = rememberPermissionState(PermissionType.CAMERA) {
        cameraManager.launch()
    }
    val galleryPermissionState = rememberPermissionState(PermissionType.GALLERY) {
        filePicker.launch()
    }

    ModalBottomSheetApp(
        onDismissRequest = component::dismiss,
    ) {
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(Res.string.action_heading),
                style = MaterialTheme.typography.titleLarge,
            )
            ActionItem(
                icon = Res.drawable.ic_camera,
                title = Res.string.action_camera,
                onClick = cameraPermissionState::launchRequestPermission,
            )
            ActionItem(
                icon = Res.drawable.ic_gallery,
                title = Res.string.action_gallery,
                onClick = galleryPermissionState::launchRequestPermission,
            )
            ActionItem(
                icon = Res.drawable.ic_generate_image,
                title = Res.string.action_generate_image,
                onClick = component::navigateToKandinskyScreen,
            )
            ActionItem(
                icon = Res.drawable.ic_delete,
                title = Res.string.action_remove_photo,
                tinColor = MaterialTheme.colorScheme.tertiary,
                onClick = component::onDeleteFileClick,
            )
        }
    }
}
