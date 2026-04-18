package com.project.recipe.addrecipe.navigation

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.recipe.addrecipe.AddRecipeScreen
import com.project.recipe.addrecipe.AddRecipeViewModel
import com.project.recipe.addrecipe.contract.AddRecipeEffect
import com.project.recipe.addrecipe.model.AddRecipeBackStack
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.addRecipeGraph(
    navigator: IngredientFarmingNavigator,
) {

    composable<IngredientRoute.AddRecipe> {
        val addRecipeViewModel: AddRecipeViewModel = hiltViewModel()
        val addRecipeState by addRecipeViewModel.collectAsState()
        val context = LocalContext.current

        addRecipeViewModel.collectSideEffect { effect ->
            when (effect) {
                AddRecipeEffect.NavigateToRecipeList -> {
                    navigator.navController.popBackStack()
                }

                AddRecipeEffect.UriIsNull -> {
                    Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show()
                }
            }
        }

        AddRecipeScreen(
            state = addRecipeState,
            onIntent = addRecipeViewModel::onIntent
        )
    }
}
