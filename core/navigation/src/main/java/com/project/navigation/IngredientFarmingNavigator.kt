package com.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class IngredientFarmingNavigator(
    val navController: NavHostController,
) {
    fun navigateToBarcodeScanner() =
        navController.navigate(IngredientRoute.BarcodeScanner)

    fun navigateToSaveIngredient() =
        navController.navigate(IngredientRoute.SaveIngredient)
}

@Composable
fun rememberIngredientFarmingNavigator(
    navController: NavHostController = rememberNavController(),
): IngredientFarmingNavigator = remember(navController) {
    IngredientFarmingNavigator(navController)
}
