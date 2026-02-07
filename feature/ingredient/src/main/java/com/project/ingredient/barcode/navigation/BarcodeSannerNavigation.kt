package com.project.ingredient.barcode.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.barcode.BarcodeScannerScreen
import com.project.ingredient.barcode.BarcodeViewModel
import com.project.ingredient.barcode.contract.BarcodeEffect
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.barcodeScannerGraph(
    navigator: IngredientFarmingNavigator
) {
    composable<IngredientRoute.BarcodeScanner> { backStack ->
        val barcodeViewModel: BarcodeViewModel = hiltViewModel()
        val barcodeState by barcodeViewModel.collectAsState()
        val context = LocalContext.current

        barcodeViewModel.collectSideEffect { effect ->
            when(effect){
                is BarcodeEffect.NavigateSaveIngredientScreen -> {
                    //TODO Navigate to Save Ingredient Screen
                }

                is BarcodeEffect.ToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        BarcodeScannerScreen(
            state = { barcodeState },
            onIntent = barcodeViewModel::onIntent,
        )
    }
}
