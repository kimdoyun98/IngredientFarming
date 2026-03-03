package com.project.shopping_cart.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

fun NavGraphBuilder.shoppingCartGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.ShoppingCart> {

    }
}
