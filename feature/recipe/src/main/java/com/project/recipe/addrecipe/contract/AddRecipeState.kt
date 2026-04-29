package com.project.recipe.addrecipe.contract

import android.net.Uri
import androidx.compose.runtime.Stable
import com.project.model.recipe.RecipeCategory
import com.project.recipe.addrecipe.model.IngredientUiModel
import com.project.recipe.addrecipe.model.RecipeStepUiModel
import com.project.recipe.addrecipe.util.AddRecipeBackStack
import com.project.recipe.addrecipe.util.RecipeSaveState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AddRecipeState(
    val recentIngredientNumber: Int = 0,
    val recentRecipeStepNumber: Int = 0,
    val currentBackstack: AddRecipeBackStack = AddRecipeBackStack.RecipePhotoScreen(),
    val photo: Uri? = null,
    val name: String = "",
    val selectedCategory: RecipeCategory? = null,
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
        ),
    val recipeSaveState: RecipeSaveState = RecipeSaveState.Idle,
    val isEnableBasicInfoNextButton: Boolean = false,
    val isEnableIngredientsNextButton: Boolean = false,
    val isEnableSaveButton: Boolean = false,
) {
    fun isEnableBasicInfoNextButton(
        name: String = this.name,
        selectedCategory: RecipeCategory? = this.selectedCategory,
        time: String = this.time,
        people: String = this.people,
    ): Boolean {
        return name.isNotBlank() &&
                selectedCategory != null &&
                time.isNotBlank() &&
                people.isNotBlank()
    }

    fun isEnableIngredientsNextButton(
        ingredients: ImmutableList<IngredientUiModel> = this.ingredients,
    ): Boolean {
        return ingredients.all {
            it.name.isNotBlank() && it.amount.isNotBlank() && !it.name.any { s -> s.isDigit() }
        }
    }

    fun isEnableSaveButton(
        recipeSteps: ImmutableList<RecipeStepUiModel> = this.recipeSteps,
    ): Boolean {
        return recipeSteps.all {
            it.description.isNotBlank()
        }
    }
}
