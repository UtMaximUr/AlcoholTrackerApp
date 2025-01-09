package com.utmaximur.permission.delegate

import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.requestAccessForMediaType
import com.utmaximur.permission.PermissionCallback
import com.utmaximur.permission.PermissionStatus
import com.utmaximur.permission.PermissionType

internal fun askCameraPermission(
    status: AVAuthorizationStatus,
    permission: PermissionType,
    callback: PermissionCallback
) {
    when (status) {
        AVAuthorizationStatusAuthorized -> {
            callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
        }

        AVAuthorizationStatusNotDetermined -> {
            return AVCaptureDevice.Companion.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                when {
                    granted -> callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    else -> callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                }
            }
        }

        AVAuthorizationStatusDenied -> {
            callback.onPermissionStatus(permission, PermissionStatus.DENIED)
        }

        else -> error("unknown camera status $status")
    }
}