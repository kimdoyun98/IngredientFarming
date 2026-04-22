package com.project.recipe.recipinfo.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.recipe.recipinfo.RecipeInfoScreen
import com.project.recipe.recipinfo.RecipeInfoViewModel
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.recipeInfoGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.RecipeInfo> {
        val recipeInfoViewModel: RecipeInfoViewModel = hiltViewModel()
        val recipeInfoState by recipeInfoViewModel.collectAsState()

        RecipeInfoScreen(
            state = recipeInfoState,
            onIntent = recipeInfoViewModel::onIntent
        )
    }
}
