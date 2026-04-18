package com.project.recipe.addrecipe.model

import androidx.annotation.StringRes
import com.project.recipe.R

sealed interface AddRecipeBackStack {
    data class RecipePhotoScreen(
        @StringRes val title: Int = R.string.add_recipe_top_app_bar_title_photo
    ) : AddRecipeBackStack
    data class RecipeBasicInfoScreen(
        @StringRes val title: Int = R.string.add_recipe_top_app_bar_title_basic
    ): AddRecipeBackStack
    data class RecipeIngredientsScreen(
        @StringRes val title: Int = R.string.add_recipe_top_app_bar_title_ingredients
    ) : AddRecipeBackStack
    data class RecipeStepsScreen(
        @StringRes val title: Int = R.string.add_recipe_top_app_bar_title_steps
    ) : AddRecipeBackStack
}
