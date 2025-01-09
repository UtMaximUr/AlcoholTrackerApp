package com.utmaximur.permission

import androidx.compose.runtime.Composable

internal expect class PermissionsManager(callback: PermissionCallback) : PermissionHandler {

    @Composable
    override fun askPermission(permission: PermissionType)

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    override fun launchSettings()
}

internal interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)
}

@Composable
internal expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager