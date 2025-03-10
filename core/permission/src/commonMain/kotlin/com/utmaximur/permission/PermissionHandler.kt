package com.utmaximur.permission

import androidx.compose.runtime.Composable

internal interface PermissionHandler {

    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()
}
