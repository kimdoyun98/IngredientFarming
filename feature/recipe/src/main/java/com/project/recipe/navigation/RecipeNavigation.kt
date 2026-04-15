package com.project.recipe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

fun NavGraphBuilder.recipeGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Recipe>{

    }
}
