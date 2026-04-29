package com.project.permission

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Stable
import com.project.model.permission.PermissionState

@Stable
class IngredientFarmingPermission(
    activity: ComponentActivity
) : Permission(activity = activity) {

    fun launchMediaImagesPermission(onResult: ((PermissionState) -> Unit)) {
        this.onMediaImagePermissionResult = onResult

        if (isTiramisuVersionHigher()) {
            launcher.launch(arrayOf(READ_MEDIA_IMAGES))
        } else {
            launcher.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
    }

    fun launchCameraPermission(onResult: ((PermissionState) -> Unit)) {
        this.onCameraPermissionResult = onResult
        launcher.launch(arrayOf(CAMERA))
    }
}
