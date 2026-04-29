package com.project.recipe.addrecipe.model

import androidx.compose.runtime.Stable

@Stable
data class RecipeStepUiModel(
    val id: Int,
    val description: String = "",
)
