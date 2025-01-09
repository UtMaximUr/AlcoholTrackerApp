package com.utmaximur.permission.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.utmaximur.permission.PermissionCallback
import com.utmaximur.permission.PermissionStatus
import com.utmaximur.permission.PermissionType
import com.utmaximur.permission.createPermissionsManager
import com.utmaximur.permission.dialog.PermissionMessageDialog
import org.jetbrains.compose.resources.stringResource


/**
 * @param permissionType - [PermissionType] тип разрешения.
 * @param permissionGranted - обратный вызов при получении разрешений от пользователя.
 */

@Composable
fun rememberPermissionState(
    permissionType: PermissionType,
    permissionGranted: () -> Unit = {}
): PermissionState {
    var launchState by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus) {
            when (status) {
                PermissionStatus.GRANTED -> permissionGranted()
                PermissionStatus.DENIED -> permissionRationalDialog = true
            }
            launchState = false
        }
    })

    when {
        launchState -> when {
            permissionsManager.isPermissionGranted(permissionType) -> {
                permissionGranted()
                launchState = false
            }

            else -> permissionsManager.askPermission(permissionType)
        }

        permissionRationalDialog -> PermissionMessageDialog(
            message = stringResource(permissionType.permissionMessage),
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true
            },
            onNegativeClick = {
                permissionRationalDialog = false
            }
        )

        launchSetting -> {
            permissionsManager.launchSettings()
            launchSetting = false
        }
    }

    return MutablePermissionState(
        isPermissionGranted = permissionsManager.isPermissionGranted(permissionType),
        permissionLaunch = { launchState = true },
        settingsLaunch = { launchSetting = true }
    )
}


@Stable
interface PermissionState {

    val isPermissionGranted: Boolean

    fun launchRequestPermission()

    fun openSettings()

}