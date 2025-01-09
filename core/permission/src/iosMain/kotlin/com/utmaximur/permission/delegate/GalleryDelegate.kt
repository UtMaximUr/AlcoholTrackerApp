package com.utmaximur.permission.delegate

import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import com.utmaximur.permission.PermissionCallback
import com.utmaximur.permission.PermissionStatus
import com.utmaximur.permission.PermissionType

internal fun askGalleryPermission(
    status: PHAuthorizationStatus,
    permission: PermissionType,
    callback: PermissionCallback
) {
    when (status) {
        PHAuthorizationStatusAuthorized -> {
            callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
        }

        PHAuthorizationStatusNotDetermined -> {
            PHPhotoLibrary.Companion.requestAuthorization { newStatus ->
                askGalleryPermission(newStatus, permission, callback)
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