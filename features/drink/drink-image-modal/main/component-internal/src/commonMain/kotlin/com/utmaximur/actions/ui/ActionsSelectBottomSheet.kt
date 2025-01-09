package com.utmaximur.actions.ui

import actions.resources.Res
import actions.resources.action_camera
import actions.resources.action_gallery
import actions.resources.action_heading
import actions.resources.action_remove_photo
import actions.resources.ic_camera
import actions.resources.ic_delete
import actions.resources.ic_gallery
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.actions.ActionsImageComponent
import com.utmaximur.media.FilePickerFileType
import com.utmaximur.media.FilePickerSelectionMode
import com.utmaximur.media.rememberCameraManager
import com.utmaximur.media.rememberFilePickerLauncher
import com.utmaximur.permission.PermissionType
import com.utmaximur.permission.state.rememberPermissionState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ActionsSelectBottomSheet(
    component: ActionsImageComponent
) {
    val cameraManager = rememberCameraManager(
        onResult = component::handleFile
    )
    val filePicker = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = component::handleFiles
    )
    val cameraPermissionState = rememberPermissionState(PermissionType.CAMERA) {
        cameraManager.launch()
    }
    val galleryPermissionState = rememberPermissionState(PermissionType.GALLERY) {
        filePicker.launch()
    }

    ModalBottomSheet(
        onDismissRequest = component::dismiss,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        sheetState = rememberModalBottomSheetState(true),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(Res.string.action_heading),
                style = MaterialTheme.typography.titleLarge
            )
            ActionItem(
                icon = Res.drawable.ic_camera,
                title = Res.string.action_camera,
                onClick = cameraPermissionState::launchRequestPermission
            )
            ActionItem(
                icon = Res.drawable.ic_gallery,
                title = Res.string.action_gallery,
                onClick = galleryPermissionState::launchRequestPermission
            )
            ActionItem(
                icon = Res.drawable.ic_delete,
                title = Res.string.action_remove_photo,
                tinColor = MaterialTheme.colorScheme.tertiary,
                onClick = component::onDeleteFileClick
            )
        }
    }
}

