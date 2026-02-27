package com.project.ingredient.barcode.ui.barcode.navigation

import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.barcode.contract.barcode.BarcodeEffect
import com.project.ingredient.barcode.ui.barcode.BarcodeScannerScreen
import com.project.ingredient.barcode.ui.barcode.BarcodeViewModel
import com.project.model.ingredient.getIndexByIngredientCategory
import com.project.model.ingredient.getIndexByIngredientStore
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
        )

        SideEffect {
            requestCameraPermission()
        }
    }
}
