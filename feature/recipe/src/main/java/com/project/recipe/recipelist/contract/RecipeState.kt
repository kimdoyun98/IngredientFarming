package com.project.recipe.recipelist.contract

import androidx.compose.runtime.Stable
import com.project.model.recipe.RecipeCategory

@Stable
data class RecipeState(
    val query: String = "",
    val selectedCategory: RecipeCategory? = null,
)
