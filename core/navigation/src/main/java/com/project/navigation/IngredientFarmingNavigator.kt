package com.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class IngredientFarmingNavigator(
    val navController: NavHostController,
) {
    fun navigateToHome() =
        navController.navigate(IngredientRoute.Home) {
            popUpTo(IngredientRoute.Home) {
                inclusive = true
            }
        }

    fun navigateToBarcodeScanner() =
        navController.navigate(IngredientRoute.BarcodeScanner)

    fun navigateToDirectInput() =
        navController.navigate(IngredientRoute.DirectInput)

    fun navigateToSaveIngredient(route: IngredientRoute) =
        navController.navigate(route) {
            launchSingleTop = true
        }

    fun navigateToManage() =
        navController.navigate(IngredientRoute.Manage)

    fun navigateToUpdateHoldIngredient(id: Int) =
        navController.navigate(IngredientRoute.UpdateHoldIngredient(id = id))

    fun navigateToShoppingCart() =
        navController.navigate(IngredientRoute.ShoppingCart)

    fun navigateToRecipe() =
        navController.navigate(IngredientRoute.Recipe)

    fun navigateToAddRecipe() =
        navController.navigate(IngredientRoute.AddRecipe)
}

@Composable
fun rememberIngredientFarmingNavigator(
    navController: NavHostController = rememberNavController(),
): IngredientFarmingNavigator = remember(navController) {
    IngredientFarmingNavigator(navController)
}
