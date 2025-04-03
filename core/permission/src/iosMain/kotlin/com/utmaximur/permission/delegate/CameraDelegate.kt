package com.utmaximur.permission.delegate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.utmaximur.permission.PermissionCallback
import com.utmaximur.permission.PermissionStatus
import com.utmaximur.permission.PermissionType
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.requestAccessForMediaType

@Composable
internal fun askCameraPermission(
    status: AVAuthorizationStatus,
    permission: PermissionType,
    callback: PermissionCallback
) {
    val scope = rememberCoroutineScope()
    when (status) {
        AVAuthorizationStatusAuthorized -> {
            callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
        }

        AVAuthorizationStatusNotDetermined -> {
            return AVCaptureDevice.Companion.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                scope.launch {
                    val result = CompletableDeferred<PermissionStatus>()
                    when {
                        granted -> result.complete(PermissionStatus.GRANTED)
                        else -> result.complete(PermissionStatus.DENIED)
                    }
                    callback.onPermissionStatus(permission, result.await())
                }
            }
        }

        AVAuthorizationStatusDenied -> {
            callback.onPermissionStatus(permission, PermissionStatus.DENIED)
        }

        else -> error("unknown camera status $status")
    }
}
