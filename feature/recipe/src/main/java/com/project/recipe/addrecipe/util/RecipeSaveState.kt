package com.project.recipe.addrecipe.util

sealed interface RecipeSaveState {
    object Idle: RecipeSaveState

    object Loading: RecipeSaveState

    object Error: RecipeSaveState
}
