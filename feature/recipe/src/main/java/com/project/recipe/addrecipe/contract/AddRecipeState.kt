package com.project.recipe.addrecipe.contract

import android.net.Uri
import androidx.compose.runtime.Stable
import com.project.model.recipe.RecipeCategory
import com.project.recipe.addrecipe.model.AddRecipeBackStack
import com.project.recipe.addrecipe.model.IngredientUiModel
import com.project.recipe.addrecipe.model.RecipeStepUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class AddRecipeState(
    val currentBackstack: AddRecipeBackStack = AddRecipeBackStack.RecipePhotoScreen(),
    val photo: Uri? = null,
    val name: String = "",
    val selectedCategory: RecipeCategory? = null,
    val category: RecipeCategory? = null,
    val time: String = "",
    val people: String = "",
    val ingredients: ImmutableList<IngredientUiModel> =
        persistentListOf(
            IngredientUiModel(
                id = 0,
            )
        ),
    val recipeSteps: ImmutableList<RecipeStepUiModel> =
        persistentListOf(
            RecipeStepUiModel(
                id = 0,
            )
        )
)
