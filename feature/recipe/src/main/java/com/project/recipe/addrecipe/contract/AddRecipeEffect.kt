package com.project.recipe.addrecipe.contract

sealed interface AddRecipeEffect {
    object NavigateToRecipeList : AddRecipeEffect
    object UriIsNull : AddRecipeEffect

    data class SaveError(val message: String = "실패"): AddRecipeEffect
}
