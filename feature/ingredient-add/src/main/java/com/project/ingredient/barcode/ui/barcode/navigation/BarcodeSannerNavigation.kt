package com.project.ingredient.barcode.ui.barcode.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.R
import com.project.ingredient.barcode.contract.barcode.BarcodeEffect
import com.project.ingredient.barcode.ui.barcode.BarcodeScannerScreen
import com.project.ingredient.barcode.ui.barcode.BarcodeViewModel
import com.project.model.ingredient.getIndexByIngredientCategory
import com.project.model.ingredient.getIndexByIngredientStore
import com.project.model.permission.PermissionState
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.barcodeScannerGraph(
    navigator: IngredientFarmingNavigator,
    cameraPermissionState: Flow<PermissionState?>,
    requestCameraPermission: () -> Unit,
) {
    composable<IngredientRoute.BarcodeScanner> { backStack ->
        val barcodeViewModel: BarcodeViewModel = hiltViewModel()
        val barcodeState by barcodeViewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        val requireCameraPermissionMessage = stringResource(R.string.require_camera_permission_message)
        val requestPermissionLabel = stringResource(R.string.request_permission_label)
        val openAppSettingsLabel = stringResource(R.string.open_app_settings_label)

        barcodeViewModel.collectSideEffect { effect ->
            when (effect) {
                is BarcodeEffect.NavigateSaveIngredientScreen -> {
                    navigator.navigateToSaveIngredient(
                        IngredientRoute.SaveIngredient(
                            name = effect.name,
                            count = 1,
                            expirationDate = "",
                            storeSelected = getIndexByIngredientStore(effect.store),
                            categorySelected = getIndexByIngredientCategory(effect.category)
                        )
                    )
                }

                is BarcodeEffect.NavigateDirectInputScreen -> {
                    navigator.navigateToDirectInput()
                }
            }
        }

        BarcodeScannerScreen(
            state = { barcodeState },
            onIntent = barcodeViewModel::onIntent,
            snackbarHostState = snackbarHostState,
        )

        LaunchedEffect(Unit) {
            cameraPermissionState.collect {
                when(it){
                    is PermissionState.Granted -> Unit

                    is PermissionState.Denied -> {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = requireCameraPermissionMessage,
                                actionLabel = requestPermissionLabel,
                                duration = SnackbarDuration.Indefinite
                            )

                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    requestCameraPermission()
                                }

                                SnackbarResult.Dismissed -> Unit
                            }
                        }
                    }

                    is PermissionState.PermanentlyDenied -> {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = requireCameraPermissionMessage,
                                actionLabel = openAppSettingsLabel,
                                duration = SnackbarDuration.Indefinite
                            )

                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    it.openAppSettings()
                                }

                                SnackbarResult.Dismissed -> Unit
                            }
                        }
                    }

                    else -> {
                        requestCameraPermission()
                    }
                }
            }
        }
    }
}
