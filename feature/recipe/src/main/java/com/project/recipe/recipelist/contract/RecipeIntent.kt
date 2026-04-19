package com.project.recipe.recipelist.contract

import com.project.model.recipe.RecipeCategory

sealed interface RecipeIntent {
    object OnTopAppBarNavigationClick : RecipeIntent
    object OnTopAppBarActionClick : RecipeIntent

    data class SearchRecipeQueryChange(val query: String) : RecipeIntent
    object SearchRecipeQueryReset : RecipeIntent
    data class SelectCategoryChip(val category: RecipeCategory?) : RecipeIntent
    data class RecipeItemClick(val itemIndex: Int) : RecipeIntent
}
