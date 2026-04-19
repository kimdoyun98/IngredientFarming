package com.project.recipe.recipelist.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.recipe.recipelist.RecipeScreen
import com.project.recipe.recipelist.RecipeViewModel
import com.project.recipe.recipelist.contract.RecipeEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.recipeGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Recipe> {
        val recipeViewModel: RecipeViewModel = hiltViewModel()
        val recipeState by recipeViewModel.collectAsState()

        recipeViewModel.collectSideEffect { effect ->
            when(effect){
                is RecipeEffect.NavigateToHome -> {
                    navigator.navigateToHome()
                }

                is RecipeEffect.NavigateToRecipeAdd -> {
                    navigator.navigateToAddRecipe()
                }

                is RecipeEffect.NavigateToRecipeInfo -> {
                    //TODO NavigateToRecipeInfo
                }
            }
        }

        RecipeScreen(
            state = recipeState,
            onIntent = recipeViewModel::onIntent
        )
    }
}
