package com.project.model.permission

sealed interface PermissionState {
    object Granted : PermissionState
    object Denied : PermissionState
    data class PermanentlyDenied(val openAppSettings: () -> Unit) : PermissionState
}
