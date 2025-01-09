package com.utmaximur.permission

import org.jetbrains.compose.resources.StringResource
import permission.resources.Res
import permission.resources.permission_message_camera
import permission.resources.permission_message_gallery


enum class PermissionType(val permissionMessage: StringResource) {
    CAMERA(Res.string.permission_message_camera),
    GALLERY(Res.string.permission_message_gallery)
}