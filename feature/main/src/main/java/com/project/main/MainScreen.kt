package com.project.main

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.project.ingredient.barcode.ui.barcode.navigation.barcodeScannerGraph
import com.project.ingredient.barcode.ui.directInput.navigation.directInputGraph
import com.project.ingredient.barcode.ui.save.navigation.saveIngredientGraph
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

@SuppressLint("RestrictedApi")
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: IngredientFarmingNavigator,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = IngredientRoute.DirectInput
    ) {
        barcodeScannerGraph(navigator = navigator)

        saveIngredientGraph(navigator = navigator)

        directInputGraph(navigator = navigator)
    }
}
