package com.utmaximur.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
internal actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}

internal actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {

    @Composable
    actual override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissions = arrayOf(
                    android.Manifest.permission.CAMERA
                )
                LaunchPermission(permission, cameraPermissions)
            }

            PermissionType.GALLERY -> {
                // Granted by default because in Android GetContent API does not require any runtime permissions
                callback.onPermissionStatus(
                    permission,
                    PermissionStatus.GRANTED
                )
            }
        }
    }

    @Composable
    actual override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> checkPermission(android.Manifest.permission.CAMERA)
            PermissionType.GALLERY -> true
        }
    }

    @Composable
    actual override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }

    @Composable
    private fun LaunchPermission(permission: PermissionType, permissions: Array<String>) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val permissionsGranted = result.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted
            }
            val permissionStatus = when {
                permissionsGranted -> PermissionStatus.GRANTED
                else -> PermissionStatus.DENIED
            }
            callback.onPermissionStatus(permission, permissionStatus)
        }
        when {
            isPermissionGranted(permission) -> callback.onPermissionStatus(permission,
                PermissionStatus.GRANTED
            )
            else -> LaunchedEffect(permissions) { launcher.launch(permissions) }
        }
    }

    @Composable
    private fun checkPermission(permission: String): Boolean {
        val context = LocalContext.current
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}