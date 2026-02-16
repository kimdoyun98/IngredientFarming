package com.project.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.home.HomeScreen
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

fun NavGraphBuilder.homeGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Home> {
        HomeScreen()
    }
}
