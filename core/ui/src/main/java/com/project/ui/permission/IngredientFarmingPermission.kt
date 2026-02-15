package com.project.ui.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.project.ui.permission.IngredientFarmingPermission.Type
import kotlin.collections.toMap

class IngredientFarmingPermission(
    private val permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
) {
    fun requestPermission(vararg permissions: Type) {
        permissionLauncher.launch(
            permissions.map { it.permission }.toTypedArray()
        )
    }

    enum class Type(val permission: String) {
        CAMERA(Manifest.permission.CAMERA)
    }
}

@Composable
fun rememberIngredientFarmingPermission(): IngredientFarmingPermission {
    val context = LocalContext.current
    var permissionState by remember {
        mutableStateOf<Map<String, Boolean>>(
            Type.entries.map { it.permission }.associateWith {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionState = permissionState.toMutableMap().apply {
                putAll(permissions)
            }.toMap()
        }
    )

    return IngredientFarmingPermission(
        permissionLauncher = permissionLauncher
    )
}
