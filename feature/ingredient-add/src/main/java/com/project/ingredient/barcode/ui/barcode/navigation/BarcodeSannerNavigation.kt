package com.project.ingredient.barcode.ui.barcode.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.R
import com.project.ingredient.barcode.contract.barcode.BarcodeEffect
import com.project.ingredient.barcode.contract.barcode.BarcodeIntent
import com.project.ingredient.barcode.ui.barcode.BarcodeScannerScreen
import com.project.ingredient.barcode.ui.barcode.BarcodeViewModel
import com.project.model.ingredient.getIndexByIngredientCategory
import com.project.model.ingredient.getIndexByIngredientStore
import com.project.model.permission.PermissionState
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.barcodeScannerGraph(
    navigator: IngredientFarmingNavigator,
    requestCameraPermission: ((PermissionState) -> Unit) -> Unit,
) {
    composable<IngredientRoute.BarcodeScanner> { backStack ->
        val barcodeViewModel: BarcodeViewModel = hiltViewModel()
        val barcodeState by barcodeViewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        val requireCameraPermissionMessage =
            stringResource(R.string.require_camera_permission_message)
        val requestPermissionLabel = stringResource(R.string.request_permission_label)
        val openAppSettingsLabel = stringResource(R.string.open_app_settings_label)
        val notFoundProductMessage = stringResource(R.string.no_search_product)
        val directInputLabel = stringResource(R.string.directInput)

        val cameraPermissionLaunch = {
            requestCameraPermission.invoke { state ->
                when (state) {
                    is PermissionState.Granted -> Unit

                    is PermissionState.Denied -> {
                        barcodeViewModel.onIntent(BarcodeIntent.CameraPermissionDenied)
                    }

                    is PermissionState.PermanentlyDenied -> {
                        barcodeViewModel.onIntent(
                            BarcodeIntent.CameraPermissionPermanentlyDenied(
                                state.openAppSettings
                            )
                        )
                    }
                }
            }
        }

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

                is BarcodeEffect.BarcodeProductEmpty, is BarcodeEffect.BarcodeResultError -> {
                    showSnackBar(
                        scope = scope,
                        snackBarHostState = snackbarHostState,
                        message = notFoundProductMessage,
                        actionLabel = directInputLabel,
                        withDismissAction = true,
                        onActionPerformed = navigator::navigateToDirectInput,
                        onDismissed = { barcodeViewModel.onIntent(BarcodeIntent.SnackBarDismissed) },
                        duration = SnackbarDuration.Long
                    )
                }

                is BarcodeEffect.CameraPermissionDenied -> {
                    showSnackBar(
                        scope = scope,
                        snackBarHostState = snackbarHostState,
                        message = requireCameraPermissionMessage,
                        actionLabel = requestPermissionLabel,
                        onActionPerformed = cameraPermissionLaunch,
                    )
                }

                is BarcodeEffect.CameraPermissionPermanentlyDenied -> {
                    showSnackBar(
                        scope = scope,
                        snackBarHostState = snackbarHostState,
                        message = requireCameraPermissionMessage,
                        actionLabel = openAppSettingsLabel,
                        onActionPerformed = effect.openAppSettings,
                    )
                }
            }
        }

        BarcodeScannerScreen(
            state = barcodeState,
            onIntent = barcodeViewModel::onIntent,
            snackbarHostState = snackbarHostState,
        )

        LaunchedEffect(Unit) {
            cameraPermissionLaunch.invoke()
        }
    }
}

private fun showSnackBar(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    withDismissAction: Boolean = false,
    onActionPerformed: () -> Unit,
    onDismissed: () -> Unit = {},
    duration: SnackbarDuration = SnackbarDuration.Indefinite
) {
    scope.launch {
        val result = snackBarHostState
            .showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration
            )
        when (result) {
            SnackbarResult.ActionPerformed -> {
                onActionPerformed()
            }

            SnackbarResult.Dismissed -> {
                onDismissed()
            }
        }
    }
}
