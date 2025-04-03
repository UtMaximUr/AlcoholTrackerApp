package com.utmaximur.permission.delegate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.utmaximur.permission.PermissionCallback
import com.utmaximur.permission.PermissionStatus
import com.utmaximur.permission.PermissionType
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary

@Composable
internal fun askGalleryPermission(
    status: PHAuthorizationStatus,
    permission: PermissionType,
    callback: PermissionCallback
) {
    val scope = rememberCoroutineScope()
    when (status) {
        PHAuthorizationStatusAuthorized -> {
            callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
        }

        PHAuthorizationStatusNotDetermined -> {
            PHPhotoLibrary.Companion.requestAuthorization { newStatus ->
                scope.launch {
                    val result = CompletableDeferred<PermissionStatus>()
                    when {
                        newStatus == PHAuthorizationStatusAuthorized -> result.complete(PermissionStatus.GRANTED)
                        else -> result.complete(PermissionStatus.DENIED)
                    }
                    callback.onPermissionStatus(permission, result.await())
                }
            }
        }

        PHAuthorizationStatusDenied -> {
            callback.onPermissionStatus(
                permission,
                PermissionStatus.DENIED
            )
        }

        else -> error("unknown gallery status $status")
    }
}
