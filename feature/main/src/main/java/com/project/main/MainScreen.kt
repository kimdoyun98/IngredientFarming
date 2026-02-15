package com.project.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.project.ingredient.barcode.ui.barcode.navigation.barcodeScannerGraph
import com.project.ingredient.barcode.ui.directInput.navigation.directInputGraph
import com.project.ingredient.barcode.ui.save.navigation.saveIngredientGraph
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.navigation.rememberIngredientFarmingNavigator
import com.project.ui.permission.IngredientFarmingPermission
import com.project.ui.permission.rememberIngredientFarmingPermission

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: IngredientFarmingNavigator = rememberIngredientFarmingNavigator(),
    ingredientFarmingPermission: IngredientFarmingPermission = rememberIngredientFarmingPermission(),
) {
    NavHost(
        navController = navigator.navController,
        startDestination = IngredientRoute.BarcodeScanner
    ) {
        barcodeScannerGraph(
            navigator = navigator,
            requestCameraPermission = {
                ingredientFarmingPermission.requestPermission(
                    IngredientFarmingPermission.Type.CAMERA
                )
            }
        )

        saveIngredientGraph(navigator = navigator)

        directInputGraph(navigator = navigator)
    }
}
