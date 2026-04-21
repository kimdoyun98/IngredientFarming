package com.project.recipe.recipelist.contract

sealed interface RecipeEffect {
    object NavigateToRecipeAdd: RecipeEffect
    object NavigateToHome: RecipeEffect
    data class NavigateToRecipeInfo(val recipeId: Int): RecipeEffect
}
