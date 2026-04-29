package com.project.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.project.model.permission.PermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Permission(
    private val activity: ComponentActivity
) {
    private val _cameraPermissionState = MutableStateFlow<PermissionState?>(null)
    val cameraPermissionState = _cameraPermissionState.asStateFlow()
    protected var onMediaImagePermissionResult: ((PermissionState) -> Unit)? = null

    protected val launcher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        for ((permission, isGranted) in permissions.entries) {
            if (isGranted) {
                updatePermissionState(permission, PermissionState.Granted)
                continue
            }
            0
            if (isLimitsPermission(activity, permission)) {
                updatePermissionState(
                    permission, PermissionState.PermanentlyDenied({
                        openAppSettingsForPermission()
                    })
                )
            } else {
                updatePermissionState(permission, PermissionState.Denied)
            }
        }
    }

    protected fun updatePermissionState(permission: String, state: PermissionState) {
        when (permission) {
            CAMERA -> {
                _cameraPermissionState.value = state
            }

            READ_MEDIA_IMAGES, READ_EXTERNAL_STORAGE -> {
                onMediaImagePermissionResult?.invoke(state)
            }
        }
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    protected fun isTiramisuVersionHigher(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    protected fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLimitsPermission(activity: Activity, permission: String): Boolean {
        return !activity.shouldShowRequestPermissionRationale(permission)
    }

    private fun openAppSettingsForPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivity(intent)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        const val READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES
        const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        const val CAMERA = Manifest.permission.CAMERA
    }
}
