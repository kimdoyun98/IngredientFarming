package com.project.recipe.recipinfo.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.recipe.recipinfo.RecipeInfoViewModel

fun NavGraphBuilder.recipeInfoGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.RecipeInfo> {
        val recipeInfoViewModel: RecipeInfoViewModel = hiltViewModel()

    }
}
