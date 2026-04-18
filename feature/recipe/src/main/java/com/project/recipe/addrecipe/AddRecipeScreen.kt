package com.project.recipe.addrecipe

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.recipe.addrecipe.add.RecipeBasicInfoScreen
import com.project.recipe.addrecipe.add.RecipeIngredientsScreen
import com.project.recipe.addrecipe.add.RecipePhotoScreen
import com.project.recipe.addrecipe.add.RecipeStepsScreen
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import com.project.recipe.addrecipe.contract.AddRecipeState
import com.project.recipe.addrecipe.model.AddRecipeBackStack
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar

@Composable
internal fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    state: AddRecipeState,
    onIntent: (AddRecipeIntent) -> Unit,
) {
    val title = remember(state.currentBackstack) {
        when (val backstack = state.currentBackstack) {
            is AddRecipeBackStack.RecipePhotoScreen -> backstack.title
            is AddRecipeBackStack.RecipeBasicInfoScreen -> backstack.title
            is AddRecipeBackStack.RecipeIngredientsScreen -> backstack.title
            is AddRecipeBackStack.RecipeStepsScreen -> backstack.title
        }
    }

    IngredientFarmingTopAppBar(
        type = AppBarType.Navigation,
        title = stringResource(title),
        onClickNavigation = { onIntent(AddRecipeIntent.Back) },
    ) { innerPadding ->
        val layoutModifier = modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)

        when (state.currentBackstack) {
            is AddRecipeBackStack.RecipePhotoScreen -> {
                RecipePhotoScreen(
                    modifier = layoutModifier,
                    state = state,
                    onIntent = onIntent
                )
            }

            is AddRecipeBackStack.RecipeBasicInfoScreen -> {
                RecipeBasicInfoScreen(
                    modifier = layoutModifier,
                    state = state,
                    onIntent = onIntent
                )
            }

            is AddRecipeBackStack.RecipeIngredientsScreen -> {
                RecipeIngredientsScreen(
                    modifier = layoutModifier,
                    state = state,
                    onIntent = onIntent
                )
            }

            is AddRecipeBackStack.RecipeStepsScreen -> {
                RecipeStepsScreen(
                    modifier = layoutModifier,
                    state = state,
                    onIntent = onIntent
                )
            }
        }
    }

    BackHandler {
        onIntent(AddRecipeIntent.Back)
    }
}

@Preview
@Composable
private fun AddRecipeScreenPreview() {
    AddRecipeScreen(
        state = AddRecipeState(
            currentBackstack = AddRecipeBackStack.RecipePhotoScreen()
        ),
        onIntent = {}
    )
}
