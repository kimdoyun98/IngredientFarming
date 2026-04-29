package com.project.recipe.recipinfo.contract

sealed interface RecipeInfoEffect {
    object NavigateToBack: RecipeInfoEffect
}
