package com.project.recipe.recipinfo.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientRoute
import com.project.recipe.recipinfo.RecipeInfoScreen
import com.project.recipe.recipinfo.RecipeInfoViewModel
import com.project.recipe.recipinfo.contract.RecipeInfoEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.recipeInfoGraph(
    popBackStack: () -> Unit,
) {
    composable<IngredientRoute.RecipeInfo> {
        val recipeInfoViewModel: RecipeInfoViewModel = hiltViewModel()
        val recipeInfoState by recipeInfoViewModel.collectAsState()

        recipeInfoViewModel.collectSideEffect { effect ->
            when(effect){
                RecipeInfoEffect.NavigateToBack -> {
                    popBackStack()
                }
            }
        }

        RecipeInfoScreen(
            state = recipeInfoState,
            onIntent = recipeInfoViewModel::onIntent
        )
    }
}
