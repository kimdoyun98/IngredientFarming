package com.project.ingredient.barcode.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.barcode.BarcodeScannerScreen
import com.project.ingredient.barcode.BarcodeViewModel
import com.project.navigation.IngredientRoute

fun NavGraphBuilder.barcodeScannerGraph() {
    composable<IngredientRoute.BarcodeScanner> {
        val barcodeViewModel: BarcodeViewModel = hiltViewModel()

        BarcodeScannerScreen(
            setBarcode = { barcodeViewModel.setBarcode(it) },
        )
    }
}
