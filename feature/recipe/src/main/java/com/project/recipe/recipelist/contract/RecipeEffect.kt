package com.project.recipe.recipelist.contract

sealed interface RecipeEffect {
    object NavigateToRecipeAdd: RecipeEffect
    object NavigateToHome: RecipeEffect
    object NavigateToRecipeInfo: RecipeEffect
}
