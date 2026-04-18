package com.project.recipe.addrecipe.contract

sealed interface AddRecipeEffect {
    object NavigateToRecipeList : AddRecipeEffect
    object UriIsNull : AddRecipeEffect
}
