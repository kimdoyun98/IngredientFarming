package com.project.ingredient.barcode.ui.barcode.navigation

import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.barcode.contract.barcode.BarcodeEffect
import com.project.ingredient.barcode.ui.barcode.BarcodeScannerScreen
import com.project.ingredient.barcode.ui.barcode.BarcodeViewModel
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.barcodeScannerGraph(
    navigator: IngredientFarmingNavigator,
    requestCameraPermission: () -> Unit,
) {
    composable<IngredientRoute.BarcodeScanner> { backStack ->
        val barcodeViewModel: BarcodeViewModel = hiltViewModel()
        val barcodeState by barcodeViewModel.collectAsState()
        val context = LocalContext.current

        barcodeViewModel.collectSideEffect { effect ->
            when (effect) {
                is BarcodeEffect.NavigateSaveIngredientScreen -> {
                    navigator.navigateToSaveIngredient(
                        IngredientRoute.SaveIngredient(
                            name = barcodeState.selectProduct.name,
                            count = 1,
                            expirationDate = "",
                            storeSelected = null,
                            categorySelected = null
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
        )

        SideEffect {
            requestCameraPermission()
        }
    }
}
