package com.project.recipe.recipinfo.contract

sealed interface RecipeInfoIntent {
    object OnTopAppBarNavigationClick: RecipeInfoIntent
}
