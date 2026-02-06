package com.project.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.project.ingredient.barcode.navigation.barcodeScannerGraph
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: IngredientFarmingNavigator,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = IngredientRoute.BarcodeScanner
    ) {

        barcodeScannerGraph(navigator = navigator)
    }
}
