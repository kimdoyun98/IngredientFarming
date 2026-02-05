package com.project.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.navigation.IngredientRoute

internal class IngredientFarmingNavigator(
    val navController: NavHostController,
) {
    fun navigateToBarcodeScanner() =
        navController.navigate(IngredientRoute.BarcodeScanner)
}

@Composable
internal fun rememberIngredientFarmingNavigator(
    navController: NavHostController = rememberNavController(),
): IngredientFarmingNavigator = remember(navController) {
    IngredientFarmingNavigator(navController)
}
