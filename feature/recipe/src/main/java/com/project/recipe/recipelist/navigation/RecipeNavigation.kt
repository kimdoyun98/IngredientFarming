package com.project.recipe.recipelist.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.project.navigation.IngredientRoute
import com.project.recipe.recipelist.RecipeScreen
import com.project.recipe.recipelist.RecipeViewModel
import com.project.recipe.recipelist.contract.RecipeEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.recipeGraph(
    navigateToHome: () -> Unit,
    navigateToAddRecipe: () -> Unit,
    navigateToRecipeInfo: (Int) -> Unit,
) {
    composable<IngredientRoute.Recipe> {
        val recipeViewModel: RecipeViewModel = hiltViewModel()
        val recipeState by recipeViewModel.collectAsState()
        val recipes = recipeViewModel.recipes.collectAsLazyPagingItems()

        recipeViewModel.collectSideEffect { effect ->
            when (effect) {
                is RecipeEffect.NavigateToHome -> {
                    navigateToHome()
                }

                is RecipeEffect.NavigateToRecipeAdd -> {
                    navigateToAddRecipe()
                }

                is RecipeEffect.NavigateToRecipeInfo -> {
                    navigateToRecipeInfo(effect.recipeId)
                }
            }
        }

        RecipeScreen(
            state = recipeState,
            recipes = recipes,
            onIntent = recipeViewModel::onIntent
        )
    }
}
