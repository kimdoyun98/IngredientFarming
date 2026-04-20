package com.project.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import com.project.model.permission.PermissionState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

@Stable
class IngredientFarmingPermission(
    private val activity: ComponentActivity
) {
    private val _cameraPermissionState = MutableStateFlow<PermissionState?>(null)
    val cameraPermissionState = _cameraPermissionState.asStateFlow()

    private val _mediaImagePermissionState =
        MutableSharedFlow<PermissionState>(extraBufferCapacity = 1)
    val mediaImagePermissionState = _mediaImagePermissionState.asSharedFlow()

    private val launcher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        permissions.entries
            .forEach { (permission, isGranted) ->

                if (isGranted) updateMediaImagePermissionState(permission, PermissionState.Granted)

                if (isLimitsPermission(activity, permission)) {
                    updateMediaImagePermissionState(
                        permission, PermissionState.PermanentlyDenied({
                            openAppSettingsForPermission()
                        })
                    )
                } else {
                    updateMediaImagePermissionState(permission, PermissionState.Denied)
                }

            }
    }

    fun launchMediaImagesPermission() {
        if (isTiramisuVersionHigher()) {
            launcher.launch(arrayOf(READ_MEDIA_IMAGES))
        } else {
            launcher.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
    }

    fun launchCameraPermission() {
        launcher.launch(arrayOf(CAMERA))
    }

    private fun openAppSettingsForPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivity(intent)
    }

    private fun updateMediaImagePermissionState(permission: String, state: PermissionState) {
        when (permission) {
            CAMERA -> {
                _cameraPermissionState.value = state
            }

            READ_MEDIA_IMAGES, READ_EXTERNAL_STORAGE -> {
                _mediaImagePermissionState.tryEmit(state)
            }
        }
    }

    private fun isLimitsPermission(activity: Activity, permission: String): Boolean {
        return !activity.shouldShowRequestPermissionRationale(permission)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES
        private const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val CAMERA = Manifest.permission.CAMERA

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
        private fun isTiramisuVersionHigher(): Boolean =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }
}
