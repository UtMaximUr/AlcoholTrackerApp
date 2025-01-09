package com.utmaximur.permission

import androidx.compose.runtime.Composable
import com.utmaximur.permission.PermissionType

internal interface PermissionHandler {

    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()
}