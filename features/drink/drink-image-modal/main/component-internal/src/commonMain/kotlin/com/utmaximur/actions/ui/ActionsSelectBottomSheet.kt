package com.utmaximur.actions.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import features.drink.drink_image_modal.main.Res
import features.drink.drink_image_modal.main.action_camera
import features.drink.drink_image_modal.main.action_gallery
import features.drink.drink_image_modal.main.action_generate_image
import features.drink.drink_image_modal.main.action_heading
import features.drink.drink_image_modal.main.action_remove_photo
import features.drink.drink_image_modal.main.ic_camera
import features.drink.drink_image_modal.main.ic_delete
import features.drink.drink_image_modal.main.ic_gallery
import features.drink.drink_image_modal.main.ic_generate_image
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ActionsSelectBottomSheet(
    component: ActionsImageComponent,
) {
    val state by component.model.collectAsState()
    val cameraManager = rememberCameraManager(
        onResult = component::addFile,
    )
    val filePicker = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = component::addFiles,
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
            enabled = state.isImageGenerationAvailable,
            onClick = component::navigateToKandinsky,
        )
        ActionItem(
            icon = Res.drawable.ic_delete,
            title = Res.string.action_remove_photo,
            tinColor = MaterialTheme.colorScheme.tertiary,
            onClick = component::deleteFile,
        )
    }
}
