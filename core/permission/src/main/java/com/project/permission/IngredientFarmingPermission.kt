package com.project.permission

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Stable
import com.project.model.permission.PermissionState

@Stable
class IngredientFarmingPermission(
    activity: ComponentActivity
) : Permission(activity = activity) {

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

    fun isGrantedCameraPermission(): Boolean {
        return checkPermission(CAMERA)
    }

    fun updateCameraPermissionState(state: PermissionState) {
        updatePermissionState(permission = CAMERA, state = state)
    }
}
